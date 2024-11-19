// Ustawienie podstawowego URL do API
const apiBaseUrl = 'http://localhost:8080/students';

// Funkcja do pobierania listy studentów
document.getElementById('getStudentsBtn').addEventListener('click', function() {
    fetch(apiBaseUrl)
        .then(response => response.json())
        .then(data => {
            const studentList = document.getElementById('studentList');
            studentList.innerHTML = ''; // Wyczyść poprzednią listę
            data.forEach(student => {
                const li = document.createElement('li');
                li.textContent = `ID: ${student.id}, Imię: ${student.name}, Nazwisko: ${student.surname}, Średnia: ${student.average}`;
                studentList.appendChild(li);
            });
        })
        .catch(error => console.error('Błąd podczas pobierania studentów:', error));
});

// Funkcja do dodawania nowego studenta
document.getElementById('addStudentForm').addEventListener('submit', function(event) {
    event.preventDefault();  // Zapobiega przeładowaniu strony

    const newStudent = {
        name: document.getElementById('name').value,
        surname: document.getElementById('surname').value,
        average: parseFloat(document.getElementById('average').value)
    };

    fetch(apiBaseUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newStudent)
    })
    .then(response => response.json())
    .then(data => {
        alert('Student dodany!');
        document.getElementById('addStudentForm').reset();  // Wyczyść formularz
    })
    .catch(error => console.error('Błąd podczas dodawania studenta:', error));
});

// Funkcja do aktualizowania studenta
document.getElementById('updateStudentForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const studentId = document.getElementById('studentId').value;
    const updatedStudent = {
        name: document.getElementById('updateName').value,
        surname: document.getElementById('updateSurname').value,
        average: parseFloat(document.getElementById('updateAverage').value)
    };

    fetch(`${apiBaseUrl}/${studentId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedStudent)
    })
    .then(response => response.json())
    .then(data => {
        alert('Student zaktualizowany!');
        document.getElementById('updateStudentForm').reset();  // Wyczyść formularz
    })
    .catch(error => console.error('Błąd podczas edytowania studenta:', error));
});

// Funkcja do usuwania studenta
document.getElementById('deleteStudentForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const studentId = document.getElementById('deleteStudentId').value;

    fetch(`${apiBaseUrl}/${studentId}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            alert('Student usunięty!');
            document.getElementById('deleteStudentForm').reset();
        } else {
            alert('Błąd podczas usuwania studenta');
        }
    })
    .catch(error => console.error('Błąd podczas usuwania studenta:', error));
});
