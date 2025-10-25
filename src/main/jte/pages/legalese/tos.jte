@import de.paladinsinn.rollenspielcons.web.MarkdownContent
@import de.paladinsinn.rollenspielcons.web.MarkdownService
@import de.paladinsinn.rollenspielcons.web.Page
@param Page page
@param MarkdownService md
@param String contextPath = ""

@template.layout(
    title = "Terms of Service",
    page = page,
    contextPath = contextPath,
    content = MarkdownContent.of(md, """
# Nutzungsbedingungen

Willkommen beiRollenspiel-Cons!

_**TBD**_
""")
)

