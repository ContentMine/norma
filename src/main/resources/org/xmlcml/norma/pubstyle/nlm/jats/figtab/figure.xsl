<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!-- Figures -->
<!-- 
          <figure typeof="sa:Image">
            <img src="Scholarly%20HTML%20%E2%80%94%20Markedly%20Smart_files/hop-less.png" height="655" width="880">
            <figcaption>
              Reconstruction of Sthenurus stirlingi, by Brian Regal; in
              «<cite><a href="http://journals.plos.org/plosone/article?id=10.1371/journal.pone.0109888">Locomotion
              in Extinct Giant Kangaroos: Were Sthenurines Hop-Less Monsters?</a></cite>», by
              Christine M. Janis, Karalyn Buttrill, Borja Figueirido.
            </figcaption>
          </figure>
 -->
	<xsl:template match="*[local-name()='fig']">
	    <figure>
			[FIGURE]<xsl:apply-templates />
	    </figure>
	</xsl:template>	

</xsl:stylesheet>