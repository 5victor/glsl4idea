# Todo list #

  * Stabilize parser
  * Priming
    * GenType function declaration templates
  * Version checking on AST level.
  * Make sure the type deducer works
  * Make sure go to definition works
  * Find usages
    * Across translation units
  * Rename
    * PSI builder
    * ooooh, in place edit


## Is this really necessary? ##
  * Complete separation of PSI and AST
    * Fix the type system with regards to function types. By this, it is meant that functions should have the type X(Y). Whereas function application should yield the return type of the called function, including overload resolution and all that.


# Future plans #
  * Static code analysis
    * condition always true/false