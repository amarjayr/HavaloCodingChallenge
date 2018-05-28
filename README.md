# havalo-coding-challenge

## Introduction

Thank you for your interest in Havalo!

This is a coding challenge designed to expose you to some of the technologies and techniques an Intern may use at Havalo.

You will need to have experience using the command line, otherwise known as a Terminal or Command Prompt. If you are unsure about how to use the command line on your computer, feel free to ask a tech savvy friend or skilled parent for help.

We ask that you complete this exercise on your own, and may use the internet as a resource, but please don't copy code (trust us, we know when solutions are copied). 

When done, we ask that you create a public repository on https://www.github.com and commit+push your code to this repository for evaluation.  Once you're ready, email the link to your repository to knockknock@havalo.com for review.

## Getting Started

To start, you're going to need a Mac or Linux box with the Java 8 SDK installed.  If you're already writing and compiling Java code on your computer, you probably already have everything you need.  If you don't have Java 8 installed, you'll need to install the SDK:

http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

Both macOS and Linux distributions ship with the `make` command.  In this exercise, `make` is used to compile your Java code and start a tiny web-server listening at http://localhost:4444.

### Compile and start the web-server

Assuming you've unzipped this exercise to a directory named havalo-coding-challenge, run the following commands in terminal to compile and start the web-server:

1. `cd havalo-coding-challenge`
2. `make`

When you run `make` from within the `havalo-coding-challenge` directory, you should see this in your terminal window:

```
[intern@havalo]~/havalo-coding-challenge$ make
rm -rf target
mkdir -p target
javac -Werror -d target HavaloCodingChallenge.java
java -cp target HavaloCodingChallenge || exit 0

Web-server started! ... press Ctrl-C to quit.
Load http://localhost:4444 in your web-browser.
```

### Making changes

Anytime you make changes, you'll need to rebuild and restart the web-server.  Press Ctrl-C in your terminal window to stop the server, and run `make` again.  Rinse and repeat anytime you make changes to the `.java` file in this exercise.

### Next Steps

Once you have the tiny web-server running successfully, hit this URL in your favorite browser:

http://localhost:4444

The rest of the instructions for this exercise will be displayed at http://localhost:4444 in your browser.

Enjoy!
