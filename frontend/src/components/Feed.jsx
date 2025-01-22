import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import defaultUserImage from '../assets/default_profile_image.jpeg'
import './Feed.css'

const Feed = ({ token }) => {
  const [users, setUsers] = useState([])
  const [error, setError] = useState(null)

  const fetchUsers = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/users', {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      })
      if (response.ok) {
        const data = await response.json()
        setUsers(data)
      } else {
        throw new Error('Failed to fetch users')
      }
    } catch (err) {
      setError(err.message)
    }
  }

  useEffect(() => {
    if (token) {
      fetchUsers()
    }
  }, [token])

  if (error) return <p>Error: {error}</p>
  if (!users.length) return <p>Loading...</p>

  return (
    <div className="feed-container">
      <h2>All Users</h2>
      <div className="users-list">
        {users.map(user => (
          <div key={user.id} className="user-item">
            <img src={user.profileImageUrl || defaultUserImage} alt={user.username} className="user-image" />
            <div className="user-details">
              <Link to={`/profile/${user.username}`} className="user-name">{user.username}</Link>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}

export default Feed
