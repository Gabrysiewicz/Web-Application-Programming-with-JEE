import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { jwtDecode } from 'jwt-decode'
import './Profile.css'

const ProfileEdit = ({ token }) => {
  const [profile, setProfile] = useState(null)
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [profileImageUrl, setProfileImageUrl] = useState('')
  const [bio, setBio] = useState('')
  const [error, setError] = useState(null)
  const navigate = useNavigate()
  const decodedToken = jwtDecode(token)
  const paramUsername = decodedToken.sub

  useEffect(() => {
    const fetchProfile = async () => {
      let url = `http://localhost:8080/api/user/username/${paramUsername}`

      try {
        const response = await fetch(url, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        })
        if (response.ok) {
          const data = await response.json()
          setProfile(data)
          setUsername(data.username)
          setEmail(data.email)
          setProfileImageUrl(data.profileImageUrl || '')
          setBio(data.bio || '')
        } else {
          throw new Error('Failed to fetch profile')
        }
      } catch (err) {
        setError(err.message)
      }
    }

    if (token && paramUsername) {
      fetchProfile()
    }
  }, [token, paramUsername])

  const handleSubmit = async (e) => {
    e.preventDefault()
    const updatedProfile = {
      id: profile.id,
      username,
      email,
      password,
      role: profile.role,
      profileImageUrl,
      bio,
    }

    try {
      const response = await fetch(`http://localhost:8080/api/user/${profile.id}`, {
        method: 'PUT',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedProfile),
      })
      if (response.ok) {
        const data = await response.json()
        setProfile(data)
        navigate(`/profile/${username}`)
      } else {
        throw new Error('Failed to update profile')
      }
    } catch (err) {
      setError(err.message)
    }
  }

  if (error) {
    return <p>Error: {error}</p>
  }

  if (!profile) {
    return <p>Loading...</p>
  }

  return (
    <div className="profile-edit-container">
      <h2>Edit Profile</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="username">Username</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="profileImageUrl">Profile Image URL</label>
          <input
            type="text"
            id="profileImageUrl"
            value={profileImageUrl}
            onChange={(e) => setProfileImageUrl(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label htmlFor="bio">Bio</label>
          <textarea
            id="bio"
            value={bio}
            onChange={(e) => setBio(e.target.value)}
          />
        </div>
        <button type="submit">Save Changes</button>
      </form>
    </div>
  )
}

export default ProfileEdit
