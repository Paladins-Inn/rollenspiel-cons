@import de.paladinsinn.rollenspielcons.web.Page
@param Page page
@param String contextPath
@param int statusCode
@param String errorMessage
@param String requestUri

@template.layout(
    page = page,
    title = "Fehler ${statusCode}",
    contextPath = contextPath,
    content = @`
        <div class="row">
            <div class="col-lg-12">
                <div class="alert alert-danger" role="alert">
                    <h4 class="alert-heading">
                        <i class="fas fa-exclamation-triangle me-2"></i>
                        Fehler ${statusCode}
                    </h4>
                    <p class="mb-0">${errorMessage}</p>
                    @if(!requestUri.isEmpty())
                        <hr>
                        <p class="mb-0"><small class="text-muted">Angeforderte URL: ${requestUri}</small></p>
                    @endif
                </div>
                
                <div class="card">
                    <div class="card-body text-center">
                        <h5 class="card-title">Was können Sie tun?</h5>
                        <p class="card-text">
                            Versuchen Sie es in ein paar Minuten erneut oder kehren Sie zur Startseite zurück.
                        </p>
                        <div class="d-grid gap-2 d-md-flex justify-content-md-center">
                            <a href="${contextPath}" class="btn btn-primary">
                                <i class="fas fa-home me-1"></i>Zur Startseite
                            </a>
                            <button onclick="history.back()" class="btn btn-secondary">
                                <i class="fas fa-arrow-left me-1"></i>Zurück
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `
)
