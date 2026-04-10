# MedTagger Rule Authoring Guide

## Overview

MedTagger is a biomedical NLP pipeline from the Mayo Clinic OHNLP program. It extracts concepts from clinical text using dictionary-based indexing, rule-based pattern matching, and context analysis (negation, historical, experiencer).

This guide covers how to create, edit, and optimize regex patterns, dictionaries, normalization mappings, match rules, and context rules for MedTagger extraction tasks.

## When to Use This Guide

Use this guide when:
- Creating a new extraction domain (e.g., social determinants, medications, procedures)
- Editing existing match rules, regexp files, or normalization mappings
- Optimizing slow regex patterns that cause performance issues
- Debugging extraction accuracy (e.g., false positives, missing concepts)
- Adding context rules for negation, historical, or experiencer attributes
- Reviewing rules for correctness before deployment

## File Format Conventions

MedTagger rules are organized into four file types that work together. Each serves a distinct purpose.

### Regexp Files (Dictionary Patterns)

Regexp files contain one **single regex per line**. These are variant spellings, synonyms, and related terms for a concept.

```
// Good - one pattern per line
fever
high temperature
febrile
elevated temperature

// Bad - multiple patterns on one line
fever|high temperature|febrile
```

Lines starting with `//` are comments. All lines are joined with `|` into one alternation during loading.

**Key principle:** Do NOT put `\b` (word boundaries) inside regexp entries — word boundaries belong in the rule file only.

### Normalization Files (Code Mapping)

Normalization files map surface forms to output codes. Format is tab-separated key-value pairs.

```
physical activity    Physical_Activity
sedentary lifestyle  Physical_Inactivity
exercise            Physical_Activity
```

The LLM generates normalized codes — typically OMOP concept IDs or domain-specific codes like `POSITIVE`/`NEGATIVE`.

### Match Rules (Extraction Logic)

Match rules define how to extract and output concepts. Format:

```
RULENAME="...",REGEXP="...",LOCATION="...",NORM="..."
```

**RULENAME** — Prefix `cm_` produces `ConceptMention` annotations; others produce generic `Match` annotations.

**REGEXP** — The extraction pattern. Use `%reKEY` to reference a regexp file. Spaces become `[\s]+` in the compiled pattern.

**LOCATION** — Constraint on where the match can occur:
- `"NA"` — no constraint (most common)
- `"UC"` — uppercase text only
- `"SEC:segmentID~"` — specific section only

**NORM** — The output value:
- Literal string: `NORM="434173"`
- Captured group: `NORM="group(1)"`
- Normalized: `NORM="%normKEY(group(1))"`
- Case transforms: `%LC%` (lowercase), `%UC%` (uppercase)
- Exclusion: `REMOVE` (exclude subsumed matches)

**Example rules:**
```
RULENAME="cm_fever",REGEXP="(?i)\b%reFEVER\b",LOCATION="NA",NORM="434173"
RULENAME="cm_physicalactivity",REGEXP="(?i)\b%rephysicalactivity\b",LOCATION="NA",NORM="%normphysicalactivity(group(1))"
```

### Context Rules (Negation and Assertion)

Context rules handle negation, historical status, and experiencer (who the statement is about). Format:

```
phrase~|~position~|~type~|~priority
```

**phrase** — Literal lowercase text, OR `regex:<pattern>` for raw regex

**position** — Where the trigger sits relative to the concept:
- `pre` — trigger is to the left, affects text to the right
- `post` — trigger is to the right, affects text to the left
- `termin` — stops context propagation
- `pseudo` — exclusion zone (concept inside is not affected)

**type** — The context type:
- `neg` — negated
- `poss` — possible
- `hypo` — hypothetical
- `hist` — historical
- `exp` — experiencer (first person)
- `histexp` — historical experiencer
- `hypoexp` — hypothetical experiencer
- `pos` — positive/affirmed

**priority** — Integer (1 = lower, 2 = higher). Higher priority overwrites lower.

**Examples:**
```
no evidence of~|~pre~|~neg~|~1
history~|~pre~|~hist~|~1
family history~|~pre~|~hist~|~2
regex:\bdenies?\b~|~pre~|~neg~|~2
```

**Important:** Context rule phrases (non-`regex:` lines) only match **whole words separated by whitespace**. `history and` will NOT match inside `social history and`. Use the `regex:` prefix for substring matching.

## Regex Performance Best Practices

Performance issues in MedTagger stem from regex backtracking. Slow patterns compound because the matcher iterates every compiled pattern against every sentence.

### Severity Ratings

| Severity | Pattern Type | Impact |
|----------|-------------|--------|
| RED | Variable-width lookbehind `(?<=.{0,N})` | ~3x slower in Java |
| RED | Nested quantifiers `(a+)+b` | Exponential on no-match strings |
| RED | Multi-variable lookbehind | Exponential in Java |
| YELLOW | Greedy `.*` in prefix.*suffix | 11-12x slower than lazy |
| YELLOW | 100+ term alternation | ~10x slower than 5-term |
| YELLOW | Bridge patterns `.{0,50}` | 1.4-1.5x overhead |
| GREEN | Anchors `^`, `$`, `\b` | Baseline fast |
| GREEN | Bounded `{0,N}` with N <= 8 | Fast |
| NEUTRAL | Possessive quantifiers | No measurable benefit |

### AVOID: Variable-Width Lookbehinds

This is the **#1 performance killer**:

```
// SLOW: (?<=.{0,10}pain)
(?<=.{0,10}pain)

// FIX: use fixed-width or restructure as forward match
(?<=pain)
```

Variable-width lookbehinds exhaust the regex engine trying all substring widths. Replace with fixed-width alternatives or restructure as forward-matching patterns.

### AVOID: Nested Quantifiers

`(\s+\S+){0,N}` with high N and nested alternation causes catastrophic backtracking:

```
// SLOW: nested quantifiers with large bounds
(problem|unable|difficulty) (\s+\S+){0,8}(speaking|responding|following)

// BETTER: reduce bounds to {0,3}
(problem|unable|difficulty) (\s+\S+){0,3}(speaking|responding)
```

### CAUTION: Greedy vs Lazy `.*`

```
// SLOW: greedy scans to end, then backtracks
Patient(?:.*)appetite

// FAST: lazy finds first match directly
Patient(?:.*?)appetite
```

### CAUTION: Large Alternations

A 100-term alternation is ~10x slower than a 5-term one. Split for readability, but note that Java optimizes a single large alternation better than multiple separate operations.

### Use Bounded Repetition

`{0,5}` is a common sweet spot. `{0,3}` is safer. Avoid anything above `{0,6}` without benchmarking.

## Common Mistakes

### Word Boundary Placement

Do NOT put `\b` inside regexp dictionary entries. The `\b` belongs in the rule file around the `%reKEY` placeholder.

```
// Rule: \b%reKEY\b — word boundary in the rule
RULENAME="cm_fever",REGEXP="(?i)\b%reFEVER\b",LOCATION="NA",NORM="434173"

// Dictionary: no \b needed inside
fever
febrile
```

### Case Sensitivity

All matching is case-insensitive. The sentence text is lowercased before matching. Do not write case-sensitive regex patterns — they will never match.

### Trailing Pipes in Regexp Files

Lines ending with `|` create an empty alternation branch. Remove blank lines and trailing `|`.

```
// Bad: trailing pipe creates empty match
fever|
high temperature|

// Good: no trailing pipe
fever
high temperature
```

### Hyphenation

The default tokenizer does NOT convert hyphens to spaces. `breast-cancer` will not match `breast cancer`. Include variants in your regexp file if needed.

### Context Checks Only the Start of a Concept

Context status is applied based only on the **first character** of a concept mention. A long concept spanning from an affirmed zone into a negated zone will be labeled based on the start position only.

### Resource Manifest

If your rule references `%reFOO` or `%normFOO`, the corresponding file must be listed in your resource manifest. Missing entries cause a fatal startup error.

## Principles for Generating Good Rules

When authoring MedTagger rules, follow these principles:

1. **Split long alternations for readability** — max ~10-15 terms per line
2. **Use `{0,3}` or `{0,4}` bounds** on `(\s+\S+)` repetition, never `{0,8}` or higher
3. **Omit `\b` inside regexp dictionary entries** — it belongs in the rule only
4. **Omit inline `(?i)` inside regexp dictionary entries** — matching is always case-insensitive
5. **Comment clearly** what each regexp file is for using `//` lines
6. **Use `REMOVE` norm** for exclusion/boilerplate patterns
7. **Use priority 2 context rules** for specific overrides
8. **Prefer simple dictionary entries** over complex multi-clause regex
9. **Never use variable-width lookbehinds** — restructure as forward-matching patterns
10. **Use lazy `.*?` instead of greedy `.*`** in patterns with both prefix and suffix
11. **Do not use possessive quantifiers** or atomic groups for performance
12. **Use context rules for negation/assertion** — not complex regex in dictionary entries
13. **Prefer literal phrases** over `regex:` in context rules — literal uses fast Aho-Corasick trie matching

## Performance Benchmark Reference

Based on testing with Java's regex engine on texts of varying lengths:

| Pattern Type | 100 chars | 1KB | 10KB | 100KB |
|-------------|-----------|-----|------|-------|
| Fixed-width lookbehind | 0.1ms | 0.3ms | 3ms | 30ms |
| Variable-width lookbehind | 0.2ms | 1ms | 10ms | 100ms |
| Lazy prefix.*suffix | 0.1ms | 0.5ms | 5ms | 50ms |
| Greedy prefix.*suffix | 0.5ms | 5ms | 50ms | 500ms |
| Bounded `{0,3}` | 0.1ms | 0.2ms | 2ms | 20ms |
| Bounded `{0,8}` | 0.2ms | 0.5ms | 5ms | 50ms |

**Note:** Java's regex engine handles many catastrophic patterns better than PCRE/Python, but variable-width lookbehinds and nested quantifiers can still cause significant slowdowns.

## Quick Reference

**Regexp file:** One pattern per line, no `\b`, joined with `|`

**Normalization file:** Tab-separated `surface form[TAB]code`

**Match rule:** `RULENAME="...",REGEXP="...",LOCATION="...",NORM="..."`

**Context rule:** `phrase~|~position~|~type~|~priority`

**Performance priority:**
- RED: Variable-width lookbehind, nested quantifiers
- YELLOW: Greedy `.*`, large alternations, bridge patterns
- GREEN: Anchors, bounded `{0,N}` with N <= 8
