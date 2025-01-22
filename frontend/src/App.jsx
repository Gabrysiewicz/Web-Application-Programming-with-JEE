import './App.css'
import AuthPage from './pages/AuthPage'
import ProfilePage from './pages/ProfilePage'
import ProfileEdit from './components/ProfileEdit'
import UploadMeme from './components/UploadMeme'
import NavigationBar from './components/NavigationBar'
import MemeManage from './components/MemeManage'
import MemeEdit from './components/MemeEdit'
import Feed from './components/Feed'
import { useState, useEffect } from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'

function App() {
  const [token, setToken] = useState(localStorage.getItem('token') || '')

  useEffect(() => {
    if (token) {
      localStorage.setItem('token', token)
    } else {
      localStorage.removeItem('token')
    }
  }, [token])

  const handleLogout = () => {
    localStorage.removeItem('token')
    setToken('')
    alert('Logged out successfully')
  }

  return (
    <Router>
      <NavigationBar token={token} onLogout={handleLogout} />
      <Routes>
        <Route path="/" element={<AuthPage setToken={setToken} />} />
        <Route path="/profile/:username" element={<ProfilePage token={token} />} />
        <Route path="/profile/edit" element={<ProfileEdit token={token} />} />
        <Route path="/upload-meme" element={<UploadMeme token={token} />} />
        <Route path="/manage" element={<MemeManage token={token} />} />
        <Route path="/edit-meme/:id" element={<MemeEdit token={token} />} />
        <Route path="/feed" element={<Feed token={token} />} />
      </Routes>
      {token ? <p>You are logged in.</p> : <p>You are not logged in.</p>}
    </Router>
  )
}

export default App
