@import de.paladinsinn.rollenspielcons.web.Page
@param Page page
@param String requestUri
@param String contextPath = ""

@template.layout(
    page = page,
    title = "Seite nicht gefunden",
    contextPath = contextPath,
    content = @`
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="error mx-auto" data-text="404">404</div>
                <p class="fs-1 text-muted">Seite nicht gefunden</p>
                <p class="fs-6 text-gray-500 mb-4">
                    Die angeforderte Seite konnte nicht gefunden werden.
                </p>
                @if(!requestUri.isEmpty())
                    <p class="text-muted"><small>Angeforderte URL: ${requestUri}</small></p>
                @endif
                <a class="btn btn-primary" href="${contextPath}">
                    <i class="fas fa-home me-1"></i>Zurück zur Startseite
                </a>
            </div>
        </div>

        <style>
        .error {
            font-size: 7rem;
            line-height: 7rem;
            color: #6c757d;
        }
        </style>
    `
)
