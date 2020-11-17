# Karma's Coverage issue demonstration app

## Instructions

First make sure you have chrome headless installed on your system. 

Then in a terminal, compile the karma-test:
```bash
$ npx shadow-cljs compile karma-test
```

Finally, download the dependencies and run the tests:
```bash
$ npm install && karma start
```

## Actual result

```
---------------|---------|----------|---------|---------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
File           | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s                                                                                                                                                             
---------------|---------|----------|---------|---------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
All files      |   24.57 |     9.28 |   12.39 |   24.92 |                                                                                                                                                                               
 karma-test.js |   24.57 |     9.28 |   12.39 |   24.92 | ...97,62003,62009-62018,62027,62033,62039,62045,62051,62057,62063-62087,62103-62109,62119-62120,62123,62126-62151,62154-62192,62196-62209,62216,62235-62237,62240-62242,62256 
---------------|---------|----------|---------|---------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

=============================== Coverage summary ===============================
Statements   : 24.57% ( 9239/37598 )
Branches     : 9.28% ( 1384/14906 )
Functions    : 12.39% ( 761/6144 )
Lines        : 24.92% ( 9164/36773 )
================================================================================
```

## Expected result

Since there is only one source file (`app.cljs`) with only 1 function (`some-function`) that is fully tested (`app_test.cljs`) we expect to have a 100% coverage reported:

```
---------------|---------|----------|---------|---------|-------------------
File           | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s                                                                                                                                                             
---------------|---------|----------|---------|---------|-------------------
All files      |  100.00 |   100.00 |  100.00 |  100.00 |                                                                                                                                                                               
        app.js |  100.00 |   100.00 |  100.00 |  100.00 |  
---------------|---------|----------|---------|---------|-------------------

=============================== Coverage summary ===============================
Statements   : 100.00% ( x/x )
Branches     : 100.00% ( 2/2 )
Functions    : 100.00% ( 1/1 )
Lines        : 100.00% ( y/y )
================================================================================
```

## Remediation

In order for Karma's coverage plugin to return an accurate analysis, we need to have the compiled source code in a separate file from the rest.

Currently, the `:karma` target makes the compiled js code to be dumped into a single file: `karma-test.js` which won't allow us to configure karma in a proper way
(see `preprocessors` in `karma.conf.js`) nor have an accurate coverage analysis.