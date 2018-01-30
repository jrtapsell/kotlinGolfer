#[How small can it get](https://codegolf.stackexchange.com/questions/151849/how-small-can-it-get/151858#151858)
## Input file
    package demo.Shrink
    //HISTORY 100(Mr. Xcoder)[59487]{Replaced `i%it==0` with `i%it<1`}
    //STARTSUB
    fun f(i:Int):Int{
        return i.toString()
            .map { it.toInt()-48 }
            .filter { it >1 && i % it < 1}
            .map { f(i/it) }
            .min() ?: i
    }
    //ENDSUB
    
    val tests = listOf(
            1 to 1,
            7 to 1,
            10 to 10,
            24 to 1,
            230 to 23,
            234 to 78,
            10800 to 1,
            10801 to 10801,
            50976 to 118,
            129500 to 37,
            129528 to 257,
            8377128 to 38783,
            655294464 to 1111)
    
    fun main(args: Array<String>) {
        for ( test in tests) {
            val computed = f(test.first)
            val expected = test.second
            if (computed != expected) {
                throw AssertionError("$computed != $expected")
            }
        }
    }
    
## Notes

- The package line is ignored by the compressor, so that multiple files can use the same function name
- The history line follows this format:
  - It starts with `//HISTORY`
  - Then for each edit:
    - The number of bytes before the edit
    - The username of the user that suggested in round brackets
    - The userId of the user that suggested in square brackets
    - The description of the edit in curly brackets
- The `//STARTSUB` starts the submission code
  - Anything before this becomes the header
- The `//ENDSUB` ends the submission code
  - Anything after this becomes the footer