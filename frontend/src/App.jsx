import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import { BrowserRouter as Router, Route, NavLink, Routes } from 'react-router-dom';
import './App.css'

function App() {
  const [count, setCount] = useState(0);

  return (
    
    <Router>
    <header>
     <ul>
      <li><NavLink to ="/signup" activeClassName="active">Signup</NavLink></li>
      <li><NavLink to ="/upload" activeClassName="active">Upload</NavLink></li>
     </ul>
      </header>
      
    <Routes>
        <Route path="/signup" element={<Signup />} />
        <Route path="/upload" element={<Upload />} />
    </Routes>

    </Router>

  )
  
}

export default App;
