# xml_yaml
Generic XML to YAML convertor

I wanted to convert "almost any convertable" XML form to YAML as simply as possible. I found no Java library to do it directly, all the options demanded intermediate conversion.

Example:
<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
<yaml>
  <primary>abc</primary>
  <secondary>def</secondary>
  <vhosts>
          <vhosts_elem>url1</vhosts_elem>
          <vhosts_elem>url2</vhosts_elem>
          <vhosts_elem>url3</vhosts_elem>
  </vhosts>
  <manage>true</manage>
  <internal_structure>
    <internal::element>elem1</internal::element>
    <internal::element1>elem2</internal::element1>
  </internal_structure>
  <external_structure>
    <external::element>elem1</external::element>
    <external::element1>elem2</external::element1>
  </external_structure>
</yaml>

Is converted to:
primary: abc
secondary: def
vhosts:
- url1
- url2
- url3
manage: true
internal_structure:
  internal::element1: elem2
  internal::element: elem1
external_structure:
  external::element: elem1
  external::element1: elem2
  
  
