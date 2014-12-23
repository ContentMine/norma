<table xsl:version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <tr>
    <th>Position</th>
    <th>Country</th>
    <th>City List</th>
    <th>Population</th>
  </tr>
  <xsl:for-each-group select="cities/city" group-by="@country">
    <tr>
      <td><xsl:value-of select="position()"/></td>
      <td><xsl:value-of select="@country"/></td>
      <td>
        <xsl:value-of select="current-group()/@name" separator=", "/>
      </td>
      <td><xsl:value-of select="sum(current-group()/@pop)"/></td>
    </tr>
  </xsl:for-each-group>
</table>
