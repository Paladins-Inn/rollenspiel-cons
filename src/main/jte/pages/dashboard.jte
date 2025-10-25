@import de.paladinsinn.rollenspielcons.web.Page
@param Page page
@param String contextPath = ""

@template.layout(
    title = "Dashboard",
    page = page,
    contextPath = contextPath,
    content = @`
        <div class="row">
            <div class="col-lg-12">
                <div class="alert alert-success" role="alert">
                    <h4 class="alert-heading">Willkommen ${page.getUserName()}!</h4>
                    <p>Sie haben sich erfolgreich angemeldet.</p>
                </div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-lg-6">
                <div class="card">
                    <div class="card-header">
                        <i class="fas fa-user me-1"></i>
                        Ihre Informationen
                    </div>
                    <div class="card-body">
                        <p><strong>Benutzername:</strong> ${page.getUserName()}</p>
                        <p><strong>E-Mail:</strong> ${page.getEmail()}</p>
                        <p><strong>Discord ID:</strong> ${page.getDiscordId()}</p>
                    </div>
                </div>
            </div>
            
            <div class="col-lg-6">
                <div class="card">
                    <div class="card-header">
                        <i class="fas fa-calendar me-1"></i>
                        Kommende Events
                    </div>
                    <div class="card-body">
                        <p>Noch keine Events geplant.</p>
                        <a href="#" class="btn btn-success">Event erstellen</a>
                    </div>
                </div>
            </div>
        </div>
    `
)
