import { BrowserRouter, Route, Routes } from 'react-router-dom'
import SignUp from './components/user/SignUp.jsx';
import Login from './components/user/Login.jsx';
import Chess from './components/chess/Chess.jsx';
import Profile from './components/user/Profile.jsx';

function App() {

  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path='/signup' element={<SignUp />}></Route>
          <Route path='/login' element={<Login/>}></Route>
          <Route path='/profile' element= {<Profile/>}></Route>
          <Route path='/chess/:gameId' element = {<Chess/>}></Route>
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
