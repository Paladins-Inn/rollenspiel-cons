@import gg.jte.Content
@import de.paladinsinn.rollenspielcons.ui.Page

@param Page page
@param String title
@param Content content
@param String contextPath

<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link rel="icon" type="image/png" href="${contextPath}assets/img/logo.png"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" />
    <link rel="stylesheet" href="${contextPath}css/styles.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <!-- Navbar Brand-->
    <a class="navbar-brand ps-3" href="${contextPath}">Rollenspiel Cons</a>
    <!-- Sidebar Toggle-->
    <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
    <!-- Navbar Search-->
    <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0">
        <div class="input-group">
            <input class="form-control" type="text" placeholder="Search for..." aria-label="Search for..." aria-describedby="btnNavbarSearch" />
            <button class="btn btn-primary" id="btnNavbarSearch" type="button"><i class="fas fa-search"></i></button>
        </div>
    </form>
    <!-- Navbar-->
    <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
        <li class="nav-item dropdown">
            @if(page.isLoggedIn())
                <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i> ${page.getUser().getName()}</a>
                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="${contextPath}user/settings">Settings</a></li>
                    <li><a class="dropdown-item" href="${contextPath}user/activities">Activity Log</a></li>
                    <li><hr class="dropdown-divider" /></li>
                    <li><a class="dropdown-item" href="${contextPath}logout">Logout</a></li>
                </ul>
            @else
                <a class="nav-link" href="${contextPath}oauth2/authorization/sso" role="button"><i class="fas fa-user fa-fw"></i> Login</a>
            @endif
        </li>
    </ul>
</nav>
<div id="layoutSidenav">
    <div id="layoutSidenav_nav">
        <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
            <div class="sb-sidenav-menu">
                <div class="nav">
                    <a class="nav-link" href="${contextPath}">
                        <span class="sb-nav-link-icon"><i class="fas fa-home"></i></span>
                        Home
                    </a>
                    @if(page.isLoggedIn())
                        <a class="nav-link" href="${contextPath}dashboard">
                            <span class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></span>
                            Dashboard
                        </a>
                    @endif

                    <div class="sb-sidenav-menu-heading">Spieler</div>

                    <div class="sb-sidenav-menu-heading">Spielleiter</div>

                    <div class="sb-sidenav-menu-heading">Con-Orga</div>

                    <div class="sb-sidenav-menu-heading">Admin</div>

                    <div class="sb-sidenav-menu-heading">Rechtliches</div>
                    <a class="nav-link" href="${contextPath}legalese/about">
                        <span class="sb-nav-link-icon"><i class="fas fa-about"></i></span>
                        Über uns
                    </a>
                    <a class="nav-link" href="${contextPath}legalese/tos">
                        <span class="sb-nav-link-icon"><i class="fas fa-book-section"></i></span>
                        Nutzungsbedingungen
                    </a>
                    <a class="nav-link" href="${contextPath}legalese/dataprotection">
                        <span class="sb-nav-link-icon"><i class="fas fa-database"></i></span>
                        Datenschutzinformationen
                    </a>
                    <a class="nav-link" href="${contextPath}legalese/imprint">
                        <span class="sb-nav-link-icon"><i class="fas fa-id-card"></i></span>
                        Impressum
                    </a>
                </div>
            </div>
        </nav>
    </div>
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">${page.getTitle()}</h1>

                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item active">${page.getTitle()}</li>
                </ol>

                <h1 class="container-fluid">${page.getTitle()}</h1>

                ${content}
            </div>
        </main>
        <footer class="py-4 bg-light mt-auto">
            <div class="container-fluid px-4">
                <div class="d-flex align-items-center justify-content-between small">
                    <div class="text-muted">Copyright &copy; 2025 rollenspiel-cons.info</div>
                </div>
            </div>
        </footer>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="${contextPath}js/scripts.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
<script src="${contextPath}assets/demo/chart-area-demo.js"></script>
<script src="${contextPath}assets/demo/chart-bar-demo.js"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
<script src="${contextPath}js/datatables-simple-demo.js"></script>
</body>
</html>
