@import de.paladinsinn.rollenspielcons.ui.Page
@param Page page
@param String contextPath

@template.layout(
    title = "About",
    page = page,
    contextPath = contextPath,
    content = @`
        <div class="row">
            <div class="col-lg-12">
                <h2>Über Rollenspiel-Cons</h2>
                <p>Diese Plattform wurde entwickelt, um Rollenspiel-Conventions zu organisieren und zu verwalten.</p>

                <h3>Features</h3>
                <ul>
                    <li>Benutzerregistrierung über Discord</li>
                    <li>Event-Management</li>
                    <li>Teilnehmer-Verwaltung</li>
                    <li>Spieltisch-Koordination</li>
                </ul>

                @if(page.getUser() != null)
                    <p><strong>Eingeloggt als:</strong> ${page.getUserName()}</p>
                    <a href="/cons/dashboard" class="btn btn-primary">Zum Dashboard</a>
                @else
                    <p>Loggen Sie sich ein, um alle Funktionen zu nutzen.</p>
                    <a href="/cons/oauth2/authorization/discord" class="btn btn-primary">Mit Discord anmelden</a>
                @endif
            </div>
        </div>
    `
)

