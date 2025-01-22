import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import './MemeEdit.css'

const MemeEdit = ({ token }) => {
  const { id } = useParams()
  const navigate = useNavigate()
  const [imageUrl, setImageUrl] = useState('')
  const [description, setDescription] = useState('')
  const [error, setError] = useState(null)

  const fetchMeme = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/meme/${id}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      })
      if (response.ok) {
        const data = await response.json()
        setImageUrl(data.imageUrl)
        setDescription(data.description)
      } else {
        throw new Error('Failed to fetch meme')
      }
    } catch (err) {
      setError(err.message)
    }
  }

  const handleUpdate = async (e) => {
    e.preventDefault()
    try {
      const response = await fetch(`http://localhost:8080/api/meme/${id}`, {
        method: 'PUT',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ imageUrl, description }),
      })
      if (response.ok) {
        navigate('/manage')
      } else {
        throw new Error('Failed to update meme')
      }
    } catch (err) {
      setError(err.message)
    }
  }

  useEffect(() => {
    if (token) {
      fetchMeme()
    }
  }, [token])

  if (error) return <p>Error: {error}</p>

  return (
    <div className="meme-edit-container">
      <h2>Edit Meme</h2>
      <form onSubmit={handleUpdate} className="meme-edit-form">
        <div className="form-group">
          <label htmlFor="imageUrl">Image URL</label>
          <input
            type="text"
            id="imageUrl"
            value={imageUrl}
            onChange={(e) => setImageUrl(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="description">Description</label>
          <textarea
            id="description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
          />
        </div>
        <button type="submit">Update Meme</button>
      </form>
    </div>
  )
}

export default MemeEdit
