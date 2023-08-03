# Validation Module

This module provides validators that are useful for spring controllers.

## Usage

```
@Controller
@Validated
class SomeController {

    @GetMapping
    fun getMethod(@SafeId val someId: String) {
        // your code here
    }
    
}
```