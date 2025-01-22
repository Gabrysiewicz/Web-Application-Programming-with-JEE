import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { jwtDecode } from 'jwt-decode'
import './Profile.css'

const UploadMeme = ({ token, onUploadSuccess }) => {
  const [imageUrl, setImageUrl] = useState('')
  const [description, setDescription] = useState('')
  const [error, setError] = useState(null)
  const navigate = useNavigate()
  const decodedToken = jwtDecode(token)
  const userId = parseInt(decodedToken.userId, 10)

  const handleSubmit = async (e) => {
    e.preventDefault()
    const memeData = {
      imageUrl,
      description,
      userId,
    }

    try {
      const response = await fetch('http://localhost:8080/api/meme', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(memeData),
      })
      if (response.ok) {
        // Add a timestamp to force profile refresh
        const refreshTimestamp = Date.now()
        navigate(`/profile/${decodedToken.sub}?refresh=${refreshTimestamp}`)
      } else {
        throw new Error('Failed to upload meme')
      }
    } catch (err) {
      setError(err.message)
    }
  }

  if (error) {
    return <p>Error: {error}</p>
  }

  return (
    <div className="upload-meme-container">
      <h2>Upload Meme</h2>
      <form onSubmit={handleSubmit}>
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
        <button type="submit">Upload</button>
      </form>
    </div>
  )
}

export default UploadMeme