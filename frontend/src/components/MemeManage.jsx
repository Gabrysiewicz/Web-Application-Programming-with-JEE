import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { jwtDecode } from 'jwt-decode';
import './MemeManage.css'

const MemeManage = ({ token }) => {
  const [memes, setMemes] = useState([])
  const [error, setError] = useState(null)
  const decodedToken = jwtDecode(token)
  const username = decodedToken.sub

  const fetchMemes = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/memes/username/${username}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      })
      if (response.ok) {
        const data = await response.json()
        setMemes(data)
      } else {
        throw new Error('Failed to fetch memes')
      }
    } catch (err) {
      setError(err.message)
    }
  }

  const handleDelete = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/api/meme/${id}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      })
      if (response.ok) {
        setMemes(memes.filter(meme => meme.id !== id))
      } else {
        throw new Error('Failed to delete meme')
      }
    } catch (err) {
      setError(err.message)
    }
  }

  useEffect(() => {
    if (token) {
      fetchMemes()
    }
  }, [token])

  if (error) return <p>Error: {error}</p>
  if (!memes.length) return <p>Loading...</p>

  return (
    <div className="meme-manage-container">
      <h2>Manage Memes</h2>
      <table className="meme-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Image</th>
            <th>Description</th>
            <th>Edit</th>
            <th>Delete</th>
          </tr>
        </thead>
        <tbody>
          {memes.map(meme => (
            <tr key={meme.id}>
              <td>{meme.id}</td>
              <td>
                <img src={meme.imageUrl} alt="Meme" className="meme-table-image" />
              </td>
              <td>{meme.description}</td>
              <td>
                <Link to={`/edit-meme/${meme.id}`} className="edit-button">Edit</Link>
              </td>
              <td>
                <button onClick={() => handleDelete(meme.id)} className="delete-button">Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

export default MemeManage
