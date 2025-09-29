@import de.paladinsinn.rollenspielcons.ui.Page
@param Page page
@param String contextPath

@template.layout(
    title = "Imprint",
    page = page,
    contextPath = contextPath,
    content = @`
        <div class="row">
            <div class="col-lg-12">
                <h2>Impressum</h2>

                <h3>Angaben gemäß §5 TMG</h3>
                <address>
                    Roland Thomas Lichti<br/>
                    Darmstädter Straße 12<br/>
                    64625 Bensheim
                </address>

                <h4>Kontakt</h4>
                <dl>
                    <dt>Telefon</dt><dd>+49 5172 9392 7171</dd>
                    <dt>Telefax</dt><dd>+49 5172 9392 7179</dd>
                    <dt>E-Mail</dt><dd><a href="mailto:info@paladins-inn.de">info@paladins-inn.de</a></dd>
                </dl>

                <h2>Verantwortlich für den Inhalt nach §55 Abs. 2 RStV</h2>
                <address>
                    Roland Thomas Lichti<br/>
                    Darmstädter Straße 12<br/>
                    64625 Bensheim
                </address>
            </div>
        </div>
    `
)

