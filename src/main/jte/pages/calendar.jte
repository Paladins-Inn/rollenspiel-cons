@import de.paladinsinn.rollenspielcons.ui.Page

@param Page page
@param String contextPath


@template.layout(
    title = "Willkommen bei den Rollenspiel-Cons",
    page = page,
    contextPath = contextPath,
    content = @`
    <div class="row">
        <div class="col-lg-12">
            <div class="card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <div>
                        <i class="fas fa-calendar me-1"></i>
                        Event-Kalender
                    </div>
                    <div>
                        <button class="btn btn-primary btn-sm" id="addEventBtn">
                            <i class="fas fa-plus me-1"></i>
                            Event hinzufügen
                        </button>
                    </div>
                </div>
                <div class="card-body">
                    <div id="calendar"></div>
                </div>
            </div>
        </div>
    </div>

    <!-- Event Details Modal -->
    <div class="modal fade" id="eventModal" tabindex="-1" aria-labelledby="eventModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="eventModalLabel">Event Details</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-8">
                            <h6 id="eventTitle"></h6>
                            <p id="eventDescription" class="text-muted"></p>

                            <div class="mb-2">
                                <i class="fas fa-clock me-2"></i>
                                <span id="eventTime"></span>
                            </div>

                            <div class="mb-2">
                                <i class="fas fa-user me-2"></i>
                                <strong>Spielleiter:</strong> <span id="eventGamemaster"></span>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card">
                                <div class="card-body text-center">
                                    <h6 class="card-title">Teilnehmer</h6>
                                    <div class="mb-2">
                                        <span id="eventCurrentPlayers" class="badge bg-primary fs-6"></span>
                                        /
                                        <span id="eventMaxPlayers" class="badge bg-secondary fs-6"></span>
                                    </div>
                                    <div class="progress mb-2">
                                        <div id="eventProgress" class="progress-bar" role="progressbar"></div>
                                    </div>
                                    <button class="btn btn-success btn-sm" id="joinEventBtn">
                                        <i class="fas fa-user-plus me-1"></i>
                                        Teilnehmen
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Schließen</button>
                    <button type="button" class="btn btn-primary" id="editEventBtn">
                        <i class="fas fa-edit me-1"></i>
                        Bearbeiten
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- FullCalendar CSS -->
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/index.global.min.css" rel="stylesheet" />

    <!-- FullCalendar JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/index.global.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/locales/de.global.min.js"></script>

    <script>
      document.addEventListener('DOMContentLoaded', function() {
        const calendarEl = document.getElementById('calendar');
        const eventModal = new bootstrap.Modal(document.getElementById('eventModal'));

        const calendar = new FullCalendar.Calendar(calendarEl, {
          initialView: 'dayGridMonth',
          firstDay: 1,
          locale: 'de',
          height: 'auto',
          headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
          },
          buttonText: {
            today: 'Heute',
            month: 'Monat',
            week: 'Woche',
            day: 'Tag'
          },
          events: '${contextPath}api/events',
          eventClick: function(info) {
            const event = info.event;
            const props = event.extendedProps;

            // Modal mit Event-Daten füllen
            document.getElementById('eventTitle').textContent = event.title;
            document.getElementById('eventDescription').textContent = props.description || '';

            const startTime = event.start.toLocaleString('de-DE', {
              weekday: 'long',
              year: 'numeric',
              month: 'long',
              day: 'numeric',
              hour: '2-digit',
              minute: '2-digit'
            });
            const endTime = event.end ? event.end.toLocaleString('de-DE', {
              hour: '2-digit',
              minute: '2-digit'
            }) : '';

            document.getElementById('eventTime').textContent =
                startTime + (endTime ? ' - ' + endTime : '');

            document.getElementById('eventGamemaster').textContent = props.gamemaster || 'N/A';
            document.getElementById('eventCurrentPlayers').textContent = props.currentPlayers || 0;
            document.getElementById('eventMaxPlayers').textContent = props.maxPlayers || 0;

            // Progress Bar berechnen
            const progress = Math.round((props.currentPlayers / props.maxPlayers) * 100);
            const progressBar = document.getElementById('eventProgress');
            progressBar.style.width = progress + '%';
            progressBar.textContent = progress + '%';

            // Button-Status basierend auf verfügbaren Plätzen
            const joinBtn = document.getElementById('joinEventBtn');
            if (props.currentPlayers >= props.maxPlayers) {
              joinBtn.disabled = true;
              joinBtn.innerHTML = '<i class="fas fa-users me-1"></i>Ausgebucht';
              joinBtn.className = 'btn btn-danger btn-sm';
              progressBar.className = 'progress-bar bg-danger';
            } else {
              joinBtn.disabled = false;
              joinBtn.innerHTML = '<i class="fas fa-user-plus me-1"></i>Teilnehmen';
              joinBtn.className = 'btn btn-success btn-sm';
              progressBar.className = 'progress-bar bg-success';
            }

            // Modal anzeigen
            eventModal.show();
          },
          eventDidMount: function(info) {
            // Tooltip für Events
            const props = info.event.extendedProps;
            info.el.title = info.event.title + '\\n' +
                (props.description || '') + '\\n' +
                'Teilnehmer: ' + props.currentPlayers + '/' + props.maxPlayers;
          }
        });

        calendar.render();

        // Event hinzufügen Button
        document.getElementById('addEventBtn').addEventListener('click', function() {
          alert('Event hinzufügen - Funktionalität noch nicht implementiert');
        });

        // Event bearbeiten Button
        document.getElementById('editEventBtn').addEventListener('click', function() {
          alert('Event bearbeiten - Funktionalität noch nicht implementiert');
        });

        // Event beitreten Button
        document.getElementById('joinEventBtn').addEventListener('click', function() {
          if (!this.disabled) {
            alert('Event beitreten - Funktionalität noch nicht implementiert');
          }
        });
      });
    </script>

    <style>
        .fc-event {
            cursor: pointer;
        }

        .fc-event:hover {
            opacity: 0.8;
        }

        #calendar {
            max-width: 100%;
        }

        .progress {
            height: 8px;
        }

        .modal-body .card {
            border: 1px solid #dee2e6;
            background-color: #f8f9fa;
        }
    </style>
`)
