# Karma's Coverage issue demonstration app

This is a fix proposal for Karma's coverage issue.

See `src/main/shadow/build/targets/karma.clj#flush-karma-test-file`.

I don't like how I handled the output file naming/creation + addition of `:output-dir` and tweak of `:output-to`.
Feel free to enhance that part. 

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

## Result

```
--------------|---------|----------|---------|---------|-------------------
File          | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s 
--------------|---------|----------|---------|---------|-------------------
All files     |     100 |      100 |     100 |     100 |                   
 karma-src.js |     100 |      100 |     100 |     100 |                   
--------------|---------|----------|---------|---------|-------------------

=============================== Coverage summary ===============================
Statements   : 100% ( 5/5 )
Branches     : 100% ( 2/2 )
Functions    : 100% ( 1/1 )
Lines        : 100% ( 5/5 )
================================================================================
```
