import React, { useState } from 'react'
import { getToken } from '../../service/user/UserService';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const navigator = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const loginUser = (e)=>{
    e.preventDefault();
    const dataInput = {username:username, password:password}
    getToken(dataInput).then(res=>{
      localStorage.setItem("token", res.data.token);
      alert("Login successful");
      navigator("/profile");
    }).catch(e=>{
      alert(e.response.data);
    })
  }

  return (
    <div style={{ fontFamily: 'Nanum Myeongjo, serif' }}>
      <div className="d-flex flex-wrap justify-content-center">
        <div className="container-fluid border border-primary m-5 rounded bg-white shadow-lg
                d-flex flex-wrap col-11 col-lg-6">
          <h2 className="w-100 text-center text-white" style={{ backgroundColor: 'darkblue' }}>Please login to Luna's chess game</h2>
          <form className="w-100 text-start ps-3 pe-3" onSubmit={loginUser}>
            <div className="form-group">
              <label htmlFor="username" className="form-label">Username </label><br />
              <input type="text" id="username" className={`form-control`} required
                value={username} onChange={e=>{setUsername(e.target.value)}} />
            </div><br />
            <div className="form-group">
              <label htmlFor="password" className="form-label">Password </label><br />
              <input type="password" id="password" className={`form-control`} required
                value={password} onChange={e=>{setPassword(e.target.value)}} />
            </div>
            <div className='d-flex flex-wrap justify-content-between'>
              <a href="/signup" className="link-secondary">Don't have an account?</a>
              <a href="/login" className="link-secondary">Forgot password?</a>
            </div>
            <br /><br />
            <div className="col-12 text-center">
              <button type="submit" className="btn btn-primary mb-2">Login</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  )
}

export default Login