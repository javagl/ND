
# nd-gen

The generators for the ND tuple and array classes. This project is not
part of any public API.

Some classes of the [ND libraries](https://github.com/javagl/ND) are
auto-generated, using [StringTemplate](http://www.stringtemplate.org/).
The templates are located in the 
[resources folder](https://github.com/javagl/ND/tree/master/nd-gen/src/main/resources). 
The naming pattern for these templates is simply that the word `Type` is
replaced by `Double`, `Int` or `Long` during the generation process, 
and optionally, some custom code parts may be inserted, which are stored
as plain `.java` files in the resource folder.

The actual generator only consists of the `NDGen` class, and the
`Type` class which stores the type-specific string replacements.
These classes are undocumented and not part of a public API, and
not supposed to be used externally. 
 