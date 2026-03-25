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
                        <p>
                            <strong>Announcement Mailinglist</strong>
                            <form action="https://lists.rollenspiel-cons.info/mlmmj.php" method="post">
                                <input name="mailinglist" type="hidden" value="announce@rollenspiel-cons.info">
                                <input name="job" type="hidden" value="subscribe">
                                <input name="redirect_failure" type="hidden" value="/dashboard">
                                <input name="redirect_success" type="hidden" value="/dashboard">
                                <input name="email" type="hidden" value="${page.getEmail()}">
                                <input type="submit" value="subscribe">
                            </form>
                            <form action="https://lists.rollenspiel-cons.info/mlmmj.php" method="post">
                                <input name="mailinglist" type="hidden" value="announce@rollenspiel-cons.info">
                                <input name="job" type="hidden" value="unsubscribe">
                                <input name="redirect_failure" type="hidden" value="/dashboard">
                                <input name="redirect_success" type="hidden" value="/dashboard">
                                <input name="email" type="hidden" value="${page.getEmail()}">
                                <input type="submit" value="unsubscribe">
                            </form>
                        </p>
                        <p>
                            <strong>Con Orga Mailinglist</strong>
                        <form action="https://lists.rollenspiel-cons.info/mlmmj.php" method="post">
                            <input name="mailinglist" type="hidden" value="orgas@rollenspiel-cons.info">
                            <input name="job" type="hidden" value="subscribe">
                            <input name="redirect_failure" type="hidden" value="/dashboard">
                            <input name="redirect_success" type="hidden" value="/dashboard">
                            <input name="email" type="hidden" value="${page.getEmail()}">
                            <input type="submit" value="subscribe">
                        </form>
                        <form action="https://lists.rollenspiel-cons.info/mlmmj.php" method="post">
                            <input name="mailinglist" type="hidden" value="orgas@rollenspiel-cons.info">
                            <input name="job" type="hidden" value="unsubscribe">
                            <input name="redirect_failure" type="hidden" value="/dashboard">
                            <input name="redirect_success" type="hidden" value="/dashboard">
                            <input name="email" type="hidden" value="${page.getEmail()}">
                            <input type="submit" value="unsubscribe">
                        </form>
                        </p>
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
