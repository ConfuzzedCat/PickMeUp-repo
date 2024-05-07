import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import React, { useState } from 'react';
import { BrowserRouter as Router, Route, NavLink, Routes } from 'react-router-dom';
import './App.css'
import Signup from './signup';

function App() {
  const [count, setCount] = useState(0)

  return (
    
    <Router>
    <header>
     <ul>
      <li><NavLink to ="/signup" activeClassName="active">Signup</NavLink></li>
     </ul>
      </header>
      <div>
        <a href="https://vitejs.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.jsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    
    <Routes>
        <Route path="/signup" element={<Signup />} />
    </Routes>

    </Router>

  )
  
  
}

export default App
