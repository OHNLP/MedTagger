[![Build Status](https://travis-ci.com/OHNLP/MedTagger.svg?branch=master)](https://travis-ci.com/OHNLP/MedTagger)

# Introduction

MedTagger contains a suite of programs that the Mayo Clinic NLP program has developed in the past three years.
It includes three major components: MedTagger for indexing based on dictionaries, MedTaggerIE for
information extraction based on patterns, and MedTaggerML for machine learning-based named entity recognition.

# Installation and Use
1. Download the latest release from https://github.com/OHNLP/MedTagger/releases
2. Extract the zip file
3. Modify the INPUTDIR, OUTPUTDIR, and RULEDIR variables in `run_medtagger_win.bat` or `run_medtagger_unix_mac.sh`, as appropriate
4. Run the batch file

# Custom Rulesets
MedTagger IE Pipelines use a custom ruleset format. An example for peripheral arterial disease
can be found under the /src/main/resources/medtaggerieresources/pad directory. These resources are what tells MedTagger
what to do/extract, and this directory is expected as input for the RULEDIR parameter 

# For Developers
1. Clone this repository
2. You will need JDK8 or above, Apache Maven, and Apache Ant installed
3. When your modifications are complete, from the project root directory:
    - Run `mvn clean install`
    - Run `ant dist`
    - A distribution zip will be created at `MedTagger.zip` in the root directory
