import { useState } from 'react'
import noMemesImage from '../assets/no_memes.jpg'
import './Profile.css'

const ProfileMemes = ({ memes, isOwner }) => {
  const [selectedMeme, setSelectedMeme] = useState(null)

  const handleMemeClick = (meme) => {
    setSelectedMeme(meme)
  }

  const handleClose = () => {
    setSelectedMeme(null)
  }

  return (
    <>
      {memes.length > 0 ? (
        <div className="memes-container">
          {memes.map((meme, index) => (
            <div key={index} className="meme-item" onClick={() => handleMemeClick(meme)}>
              <img src={meme.imageUrl} alt="Meme" className="meme-image" />
            </div>
          ))}
        </div>
      ) : (
        <div className="no-memes-container">
          <img src={noMemesImage} alt="No Memes" className="no-memes-image" />
          {isOwner && (
            <p>
              Share Photos
              <br />
              When you share photos, they will appear on your profile.
            </p>
          )}
        </div>
      )}

      {selectedMeme && (
        <div className="meme-overlay" onClick={handleClose}>
          <div className="meme-overlay-content">
            <img src={selectedMeme.imageUrl} alt="Meme" className="meme-overlay-image" />
          </div>
        </div>
      )}
    </>
  )
}

export default ProfileMemes
