[![Build Status](https://travis-ci.com/OHNLP/MedTagger.svg?branch=master)](https://travis-ci.com/OHNLP/MedTagger)

# MedTagger

MedTagger contains a suite of programs that the Mayo Clinic NLP program has developed in the past three years.
It includes three major components: MedTagger for indexing based on dictionaries, MedTaggerIE for
information extraction based on patterns, and MedTaggerML for machine learning-based named entity recognition.

# Installation and Use
#### Video demo: https://vimeo.com/392331446
1. Download the latest release from https://github.com/OHNLP/MedTagger/releases
2. Extract the zip file
3. Modify the `INPUTDIR`, `OUTPUTDIR`, and `RULEDIR` variables in `run_medtagger_win.bat` or `run_medtagger_unix_mac.sh`, as appropriate
    - `INPUT_DIR`: full directory path of input folder 
    - `OUTPUT_DIR`: full directory path of output folder
    - `RULES_DIR`: full directory path of 'Rule' folder
    
    Example for Mac:
    ```
    INPUTDIR="/Users/username/Desktop/demo/input"
    OUTPUTDIR="/Users/username/Desktop/demo/output"
    RULEDIR="/Users/username/Desktop/MedTagger/medtaggerieresources/pad"
    ```
    
    Example for Windows:
    ```
    INPUTDIR="C:\Users\username\Desktop\input"
    OUTPUTDIR="C:\Users\username\Desktop\input\output"
    RULEDIR="C:\Users\username\Desktop\MedTagger\medtaggerieresources\pad"
    ```
    
4. Run the batch file

    Mac/linux: 
    ```
    run_medtagger_unix_mac.sh
    ```
    
    Windows: 
    
    ```
    run_medtagger_win.bat
    ```
    
# Custom Rulesets
MedTagger IE Pipelines use a custom ruleset format. An example for peripheral arterial disease
can be found under the `/src/main/resources/medtaggerieresources/pad` directory. These resources are what tells MedTagger
what to do/extract, and this directory is expected as input for the RULEDIR parameter 

# For Developers
1. Clone this repository
2. You will need JDK8 or above, Apache Maven, and Apache Ant installed
3. When your modifications are complete, from the project root directory:
    - Run `mvn clean install`
    - Run `ant dist`
    - A distribution zip will be created at `MedTagger.zip` in the root directory
