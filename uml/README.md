# Transformations Between VDM and UML

## Contents


## Title 1

## Basic Data Types
Basic data types are included in PlantUML as the type of a class attribute.


## Compound Types

### multiplicity types
Non associative multiplicity types are included as the type of a class attribute.




#### set:
non associative: set of element
associative: class -> "*" object

#### seq:
non associative: seq of element
associative: class -> "(*)" object

#### seq1:
non associative: seq1 of element
associative: class "1" -> "(*)" object

### qualified association types

#### map:
non associative: "mapName" map qualifier to multiplicity element
associative: class "[qualifier]" -> multiplicity object

#### inmap:
non associative: "mapName" map qualifier to multiplicity element
associative: class "[(qualifier)]" -> multiplicity object

### Other compound types


#### optinal type: "[]"
#### composite type: "::" 
#### union type: "|"
#### product type: "*"


## TODOs

- Fix declated types showing up as the "type of the type" instead of the name of type when used with an instance variable. Eg. Qualification is defined as a union, reqQuali is the type "Qualification", but shows up as just being a union.






--------------------------------#README NOTES------------------------------------------------

Syntax:    type = association type
	    
	   association type = general association type
			    | qualified association type

	   general association type = identifier [qualification clause] --> [inheritance clause] test : -collection0


## Bi-directional mapping:


### Basic Data Types

There is a one-to-one mapping of basic VDM data types between VDM and PlantUML, 
with the VDM symbol for the construct being represented as the type in a UML attribute or operation.


### Set and Sequence types

If the type of the elements in a set is a Class, the set is considered to be associative. 
If the type of the elements in a set is any other type, including a compound type that refer to a class, the set is considered to be non-associative.



#### Non-Associative sets and sequences:




#### Associative sets and sequences:
	


### Non bi-directional mapping: VDM2UML

#### VDM Structure Abstraction

To avoid excessive information in the class diagram certain VDM structures are abstracted away.

##### Set and Sequence Types

set of set of ClassA (defined in ClassA, no association)

map set of set of alarm to set of set of expert

##### Map Types


##### Other Compound types
The product, composite, union, optional and product compound types are abstracted in their UML representation, 
by omiting the subtypes of the compound type in the UML model and instead showing the types using VDM2UML type signifiers.


VDM2UML type signifiers are an abstract representation of VDM types. 
The type signifiers consists type tokens which are special strings of characters 
that denote the type of the type signifier. In the case of union and product types, 
the number of subtypes determine the number of type tokens used in the type signifier,
with N subtypes leading to N-1 type tokens in the UMl model.


The VDM2UML type signifiers are as follows: 

Optinal type: "[]"
Composite type: "::"
Union type: "|"
Product type: "*"

If the number type tokens needed to create a type signifier exceeds 5, 
the signifier is instead a single type token followed by three dots.
The value of the subtypes is not considered, only top level compound type is represented in the UML model.


Examples: 

VDM							UML

expr = [char] | nat | seq of char * bool;		-Expr0 : ||

var : nat * nat * nat * nat * nat * nat * nat;		-Expr1 : *...




## Non bi-directional mapping: UML2VDM


in keyword
type signifiers are optional

