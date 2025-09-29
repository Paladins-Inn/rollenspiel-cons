@import de.paladinsinn.rollenspielcons.ui.Page
@param Page page
@param String contextPath
@param boolean loginError = false
@param String errorMessage = ""
@param boolean logoutSuccess = false
@param String logoutMessage = ""

@template.layout(
    page = page,
    title = "Anmeldung",
    contextPath = contextPath,
    content = @`
        <div class="row justify-content-center">
            <div class="col-lg-5">
                @if(loginError)
                    <div class="alert alert-danger" role="alert">
                        <i class="fas fa-exclamation-triangle me-2"></i>
                        ${errorMessage}
                    </div>
                @endif
                
                @if(logoutSuccess)
                    <div class="alert alert-success" role="alert">
                        <i class="fas fa-check-circle me-2"></i>
                        ${logoutMessage}
                    </div>
                @endif
                
                <div class="card shadow-lg border-0 rounded-lg mt-5">
                    <div class="card-header">
                        <h3 class="text-center font-weight-light my-4">
                            <i class="fas fa-sign-in-alt me-2"></i>
                            Anmeldung
                        </h3>
                    </div>
                    <div class="card-body">
                        <p class="text-center mb-4">
                            Wählen Sie einen Anbieter für die Anmeldung:
                        </p>
                        
                        <div class="d-grid gap-2">
                            <a href="${contextPath}oauth2/authorization/discord" class="btn btn-primary btn-lg">
                                <i class="fab fa-discord me-2"></i>
                                Mit Discord anmelden
                            </a>
                            
                            <a href="${contextPath}oauth2/authorization/paladins-inn" class="btn btn-success btn-lg">
                                <i class="fas fa-key me-2"></i>
                                Mit Paladins Inn anmelden
                            </a>
                        </div>
                        
                        <hr class="my-4">
                        
                        <div class="text-center">
                            <small class="text-muted">
                                Durch die Anmeldung stimmen Sie unseren 
                                <a href="${contextPath}legalese/tos">Nutzungsbedingungen</a> zu.
                            </small>
                        </div>
                    </div>
                    <div class="card-footer text-center py-3">
                        <div class="small">
                            <a href="${contextPath}">Zurück zur Startseite</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `
)
