# Contributing to norma

Thank you for taking the time to contribute! :+1:

This is a set of guidelines for contributing to norma. You don't need to follow them as strict rules, use your best judgement and feel free to propose changes to this document as well via a pull request.

#### Table of Contents

[Basics](#basics)

[How can I contribute?](#how-can-i-contribute)

[Local testing](#local-testing)

## Basics

norma is built in Java. If you want an introduction on how to work on a project like this, you can find a beginners tutorial [here](https://www.codecademy.com/learn/learn-java).

## How can I contribute?

### Report bugs

If you encounter a bug, please let us know. You can raise a new issue [here](https://github.com/ContentMine/norma/issues). Please include as many information in your report as possible, to help maintainers reproduce the problem.

* A clear and descriptive title
* Describe the exact steps which reproduce the problem, e.g. the query you entered.
* Describe the behaviour following those steps, and where the problem occurred.
* Explain where it was different from what you expected to happen.
* Attach additional information to the report, such as error messages, or corrupted files.
* Add a `bug` label to the issue.

Before submitting a bug, please check the [list of existing bugs](https://github.com/ContentMine/norma/issues?q=is%3Aissue+is%3Aopen+label%3Abug) whether there is a similar issue open. You can then help by adding your information to an existing report.

### Fixing bugs or implementing new features

If you're not sure where to start, have a look at issues that have a `help wanted` label - here is a [list](https://github.com/ContentMine/norma/issues?q=is%3Aissue+is%3Aopen+label%3A%22help+wanted%22).

### Suggesting features or changes

There is always room for improvement and we'd like to hear your perspective on it.

Before creating a pull request, please raise an issue to discuss the proposed changes first. We can then make sure to make best use of your efforts.

## Local testing

In order to set up your development environment for norma, you need a recent verion of [Java](https://www.java.com/) (at least 1.7), and [Maven 3](https://maven.apache.org/download.cgi).

1. Create a fork on [github](https://help.github.com/articles/fork-a-repo/).
1. Create a [new branch](https://www.atlassian.com/git/tutorials/using-branches/git-checkout) with a descriptive name.
1. Work on your changes, and make regular commits to save them.
1. Test your changes:
  1. build with `mvn clean install`
  1. check error logs and if necessary review test outputs
1. When your changes work as intended, push them to your repository and [create a pull request](https://www.atlassian.com/git/tutorials/making-a-pull-request).
1. We will then review the pull request and merge it as soon as possible. If problems arise, they will be discussed within the pull request.

## License

All content in the issue tracker and the wiki is offered under the terms of the [Creative Commons Attribution 4.0 (CC BY 4.0) license](https://creativecommons.org/licenses/by/4.0/). Please do not contribute issues, comments, or wiki content to this repository unless you consent to the application of that license.
