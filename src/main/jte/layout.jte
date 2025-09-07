@import gg.jte.Content
@import de.paladinsinn.rollenspielcons.ui.Page

@Param Page page
@Param Content content

<html lang="en">
    <head>
        <title>${page.getTitle()}</title>

        @if (page.getDescription() != null)
        <meta name="description" content="${page.getDescription()}"/>
        @endif

        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <script src="https://cdn.tailwindcss.com"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
    </head>
    <body>
        <h1>${page.getTitle()}</h1>

        ${content}
    </body>
</html>
