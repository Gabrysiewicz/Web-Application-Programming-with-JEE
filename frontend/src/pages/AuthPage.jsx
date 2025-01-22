import Welcome from '../components/Welcome'
import Register from '../components/Register'
import Login from '../components/Login'
import './AuthPage.css'

const AuthPage = ({ setToken }) => {
  const handleRegister = async ({ username, email, password }) => {
    localStorage.removeItem('token')
    setToken('')
    const response = await fetch('http://localhost:8080/api/auth/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ username, email, password }),
    })
    if (response.ok) {
      alert('Registration successful')
    } else {
      alert('Registration failed')
    }
  }

  const handleLogin = async ({ usernameOrEmail, password }) => {
    localStorage.removeItem('token')
    setToken('')
    const response = await fetch('http://localhost:8080/api/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ usernameOrEmail, password }),
    })
    if (response.ok) {
      const token = await response.text()
      localStorage.setItem('token', token)
      setToken(token)
      alert('Login successful')
    } else {
      alert('Login failed')
    }
  }

  return (
    <div className="auth-container">
      <div className="auth-header">
        <Welcome />
      </div>
      <br/>
      <div className="auth-form">
        <Register onRegister={handleRegister} />
      </div>
      <br/>
      <br/>

      <div className="auth-form">
        <Login onLogin={handleLogin} />
      </div>
    </div>
  )
}

export default AuthPage
