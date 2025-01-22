import { Link, useNavigate } from 'react-router-dom'
import { jwtDecode } from 'jwt-decode';
import './NavigationBar.css'
import defaultUserImage from '../assets/default_profile_image.jpeg'

const NavigationBar = ({ token, onLogout }) => {
  const navigate = useNavigate()
  let username = ''
  let profileImageUrl = defaultUserImage

  if (token) {
    const decodedToken = jwtDecode(token)
    username = decodedToken.sub
    profileImageUrl = decodedToken.profileImageUrl || defaultUserImage
  }

  const handleLogout = () => {
    localStorage.removeItem('token')
    onLogout('')
    navigate('/')
  }

  return (
    <nav className="navigation-bar">
      <div className="nav-container">
        <Link to="/" className="nav-logo">MyApp</Link>
        <Link to="/feed" className="nav-link nav-feed">Feed</Link>
        <div className="nav-links">
          {token ? (
            <>
              <Link to={`/profile/${username}`} className="nav-link">
                <img src={profileImageUrl} alt="Profile" className="nav-profile-image" />
                {username}
              </Link>
              <button onClick={handleLogout} className="nav-link">Logout</button>
            </>
          ) : (
            <Link to="/" className="nav-link">Login</Link>
          )}
        </div>
      </div>
    </nav>
  )
}

export default NavigationBar
