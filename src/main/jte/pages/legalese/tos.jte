@import de.paladinsinn.rollenspielcons.web.Page
@param Page page
@param String contextPath

@template.layout(
    title = "Terms of Service",
    page = page,
    contextPath = contextPath,
    content = @`
        @raw(md.toHtml("""
        # Nutzungsbedingungen

        Willkommen bei Rollenspiel-Cons! Diese Nutzungsbedingungen regeln Ihre Nutzung unserer Plattform zur Organisation und Verwaltung von Rollenspiel-Conventions.
        Durch die Nutzung unserer Dienste stimmen Sie diesen Bedingungen zu.
        """))
    `
)

