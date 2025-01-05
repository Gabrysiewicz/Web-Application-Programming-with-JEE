document.addEventListener("DOMContentLoaded", () => {

    // Pobieranie listy studentów
    const getStudentsBtn = document.getElementById("getStudentsBtn");
    const studentList = document.getElementById("studentList");

    getStudentsBtn.addEventListener("click", async () => {
        try {
            const response = await fetch("/students");
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const students = await response.json();
            studentList.innerHTML = ""; // Wyczyść listę
            students.forEach(student => {
                const li = document.createElement("li");
                li.textContent = `ID: ${student.id}, Imię: ${student.name}, Nazwisko: ${student.surname}, Średnia: ${student.average}`;
                studentList.appendChild(li);
            });
        } catch (error) {
            console.error("Error fetching students:", error);
        }
    });

    // Dodawanie studenta
    const addStudentForm = document.getElementById("addStudentForm");

    addStudentForm.addEventListener("submit", async (event) => {
        event.preventDefault();
        const formData = new FormData(addStudentForm);
        const student = {
            name: formData.get("name"),
            surname: formData.get("surname"),
            average: parseFloat(formData.get("average"))
        };

        try {
            const response = await fetch("/students/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(student)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const newStudent = await response.json();
            alert(`Dodano studenta: ID ${newStudent.id}, Imię: ${newStudent.name}`);
            addStudentForm.reset();
        } catch (error) {
            console.error("Error adding student:", error);
        }
    });

    // Edytowanie studenta
    const updateStudentForm = document.getElementById("updateStudentForm");

    updateStudentForm.addEventListener("submit", async (event) => {
        event.preventDefault();
        const formData = new FormData(updateStudentForm);
        const studentId = formData.get("studentId");
        const updatedStudent = {
            name: formData.get("updateName"),
            surname: formData.get("updateSurname"),
            average: parseFloat(formData.get("updateAverage"))
        };

        try {
            const response = await fetch(`/students/${studentId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(updatedStudent)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const result = await response.text();
            alert(result);
            updateStudentForm.reset();
        } catch (error) {
            console.error("Error updating student:", error);
        }
    });

    // Usuwanie studenta
    const deleteStudentForm = document.getElementById("deleteStudentForm");

    deleteStudentForm.addEventListener("submit", async (event) => {
        event.preventDefault();
        const formData = new FormData(deleteStudentForm);
        const studentId = formData.get("deleteStudentId");

        try {
            const response = await fetch(`/students/${studentId}`, {
                method: "DELETE"
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const result = await response.text();
            alert(result);
            deleteStudentForm.reset();
        } catch (error) {
            console.error("Error deleting student:", error);
        }
    });

});