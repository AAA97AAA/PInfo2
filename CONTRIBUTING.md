# How To Contribute

The following is a set of guidelines for contributing to SoftAware, which are hosted in the [SoftAware](https://github.com/unige-pinfo-2018/PInfo2) on GitHub. These are mostly guidelines, not rules. Use your best judgment, and feel free to propose changes to this document in a pull request.



## Table of Contents

* [Code of Conduct](#code-of-conduct)

* [I don't want to read this whole thing, I just have a question!!!](#i-dont-want-to-read-this-whole-thing-i-just-have-a-question)
* [Coding Conventions](#coding-conventions)

    * [Git Commit Messages](#git-commit-messages)
    * [Java](#java)


* [Sources](#sources)



## Code of Conduct

This project and everyone participating in it is governed by the [Code of Conduct](CODE_OF_CONDUCT.md). By participating, you are expected to uphold this code.



## I don't want to read this whole thing I just have a question!!!

If you have any question (related to the project), ask in this order:

1. GIYF ([Google](https://www.google.ch/?hl=fr) Is Your Friend)
2. RTFM (Read The Fu**ing Manual)
3. Read the [course](https://github.com/unige-pinfo-2018/Slides/tree/master/Steve) or the [exercises](https://github.com/unige-pinfo-2018/Slides/tree/master/Stefan)
4. Ask friends, colleagues, parents, ...
5. Ask [StackOverflow](https://stackoverflow.com/)
6. Ask [the assistant](mailto:stefan.klikovits@unige.ch)
7. Ask [the teacher](mailto:steve.hostettler@gmail.com)



##  Coding Conventions

Contributors should follow these rules for proper coding

### Git Commit Messages

* Use the present tense ("Add feature" not "Added feature")

* Use the imperative mood ("Move cursor to..." not "Moves cursor to...")

### Java

* No return to the line before { (only one space)
 ```java
 public void methodName() {
     // ...
 }

 // instead of

 public void methodName()
 {
     // ...
 }

 // or

 public void methodName(){
     // ...
 }
 ```
* Use parentheses if it improves code clarity

* Use camelCase for method's (except Constructor) and variable's names and CamelCase for class's name
``` java
public class ClassName {

    private int variableName;

    public void methodName() {
        // ...
    }
}
```

* Capitalize initialisms and acronyms in names, except for the first word, which should be lower-case

    * `getURI` instead of `getUri`
    * `uriToOpen` instead of `URIToOpen`


* Put spaces after list items, method parameters, around operators and at the beginning of a comment

    * `[1, 2, 3]` instead of `[1,2,3]`
    * `x += 1` instead of `x+=1`
    * `\\ Hello` instead of `\\Hello`


* Include at least one line of whitespace between methods

* Add a description before the class
 ``` java
 /**
 * description ...
 *
 * @author John Doe
 *
 */
 public class ClassName {
     // ...
 }
 ```
* A class should be organized like this:
 ``` java
 package packagename;

 import java;
 //...

 /**
 * description ...
 *
 * @author John Doe
 *
 */
 public class ClassName {

    private int variableName;
    // ...

    /***** Constructors *****/

    public ClassName() {
    }

    public ClassName(int variableName) {
        super();
		this.variableName = variableName;
        // ...
	}

    /***** Manipulation *****/

    public void methodName(int arg) {
        // ...
    }

    // ...

    /***** Getters/Setters *****/

    public int getVariableName() {
		return variableName;
	}

	public void setVariableName(int variableName) {
		this.variableName = variableName;
	}

    // ...

    /***** Utility *****/

    @Override
	protected Object clone() throws CloneNotSupportedException {
        // ...
    }

    @Override
	public boolean equals(Object obj) {
        // ...
    }

    @Override
	public String toString() {
        // ...
    }

    // ...
 }
 ```



## Sources

* [The Atom editor contribution guidelines](https://github.com/atom/atom/blob/master/CONTRIBUTING.md)
* [The Ruby on Rails contribution guidelines](https://github.com/rails/rails/blob/master/CONTRIBUTING.md)
* [The Open Government contribution guidelines](https://github.com/opengovernment/opengovernment/blob/master/CONTRIBUTING.md)
