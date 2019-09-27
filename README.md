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

# For Developers
1. Clone this repository
2. You will need JDK8 or above, Apache Maven, and Apache Ant installed
3. When your modifications are complete, from the project root directory:
    - Run `mvn clean install`
    - Run `ant dist`
    - A distribution zip will be created at `MedTagger.zip` in the root directory