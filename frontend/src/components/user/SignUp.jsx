import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import { newUser, sendCode } from '../../service/user/UserService';
import { verifyEmail } from '../../service/user/UserService';

const SignUp = () => {
  const navigator = useNavigate();
  const [username, setUsername] = useState('');
  const [checkValidUn, setValidUn] = useState('');

  const handleUnInput = (e)=>{
    const value = e.target.value;
    setUsername(value);

    const regexUn = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{4,10}$/;

    if (value.length ===0) setValidUn('');
    else if (!regexUn.test(value)) setValidUn('is-invalid');
    else setValidUn('is-valid');
  }

  const [password, setPassword] = useState('');
  const [checkValidPw, setValidPw] = useState('');

  const handlePwInput = (e)=>{
    const valuePw = e.target.value;
    setPassword(valuePw);

    const regexPw = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*/?])[A-Za-z\d!@#$%^&*/?]{6,15}/
    if (valuePw.length ===0) setValidPw('');
    else if (!regexPw.test(valuePw)) setValidPw('is-invalid');
    else setValidPw('is-valid');
  }

  const [checkPw, setCheckPw] = useState('');
  const [email, setEmail] = useState("");
  const [checkEmail, setCheckEmail] = useState("");
  const [fullname, setName] = useState("");
  const [codeVerify, setCodeVerify] = useState("d-none");
  const [code, setCode] = useState("");

  const [timeLeft, setTimeLeft] = useState(10*60); 
  const [time, setTimePlay] = useState("d-none");
  const [resendButton, setResend] = useState("d-none");
  
  useEffect(()=>{
    // if (codeVerify!== "d-none"){
      const interval = setInterval(() => {
        setTimeLeft((prevTime) => {
          if (prevTime <= 0) {
            setTimePlay("d-none");
            setResend("");
          }; 
          return prevTime - 1
        });
      }, 1000);
    // }
    return () => clearInterval(interval); 
  }, [time, resendButton])

  const codeClick = (e)=>{
    e.preventDefault();
    const emailInput = {email: email}
    sendCode(emailInput).then(res=>{
      // alert(res.data);
      setCodeVerify("");
      setTimePlay("");
      setTimeLeft(10*60-30);
      setResend("d-none");
    }).catch(e=>{
      alert(e.response.data);
      if (e.response.data === "Email has already verified")
        setCheckEmail("is-valid");
    })

    // Stop when the timer reaches zero
    
  }

  const formatTime = (seconds)=>{
    const minutes = Math.floor(seconds/60);
    const secs = seconds%60;
    return `${minutes}:${secs < 10 ? "0" : ""}${secs}`;
  }

  const verifySubmit = (e)=>{
    e.preventDefault();
    if (checkEmail==="is-valid") {
      alert("Your email is already verified!!!");
      return;
    }
    verifyEmail({email:email, code:code}).then(res=>{
      alert(res.data);
      setCodeVerify("d-none");
      setCheckEmail("is-valid");
    }).catch(e=>{
      alert(e.response.data);
      // console.log(e.response.data);
      
    })
  }

  const resendClick = (e)=>{
    e.preventDefault();
    sendCode({email:email}).then(res=>{
      alert(res.data);
      setCodeVerify("");
      setResend("d-none");
      setTimePlay("");
      setTimeLeft(10);
    }).catch(e=>{
      alert(e.response.data);
    })
  }

  const createUser = (e)=>{
    e.preventDefault();
    const inputData = {
      username: username,
      password: password, 
      confirmPw: checkPw,
      email: email,
      name: fullname,
    }

    newUser(inputData).then(res=>{
      alert("Sign in successfully!!!")
      navigator("/login")
    }).catch(e=>{
      alert(e.response.data);
    })
  }
  
  return (
    <div style={{ fontFamily: 'Nanum Myeongjo, serif' }}>
      <div className="d-flex flex-wrap justify-content-center">
        <div className="container-fluid border border-primary rounded bg-white shadow-lg
                d-flex flex-wrap col-11 col-lg-9">
          <h2 className="w-100 text-center text-white" style={{ backgroundColor: 'darkblue' }}>Welcome to Luna's chess game</h2>
          <form className="w-100 text-start ps-3 pe-3" onSubmit={createUser}>
            <div className="form-group">
              <label htmlFor="username" className="form-label">Username </label><br />
              <input type="text" id="username" className={`form-control ${checkValidUn}`} required
                value={username} onChange={handleUnInput} />
              <div className="invalid-feedback">
                Username should contain at least one letter and one digit.<br />
                The length of username is between 4 and 10 characters.
              </div>
            </div><br />
            <div className="form-group">
              <label htmlFor="password" className="form-label">Password </label><br />
              <input type="password" id="password" className={`form-control ${checkValidPw}`} required
                value={password} onChange={handlePwInput} />
              <div className="invalid-feedback">
                Password should contain at least one letter, one digit
                and one special character (!@#$%^&*/?).<br />
                The length of password is between 6 and 15 characters.
              </div>
            </div><br />
            <div className="form-group">
              <label htmlFor="pwCheck" className="form-label">Confirm password </label><br />
              <input type="password" id="pwCheck" className="form-control" required
                value={checkPw} onChange={(e) => { setCheckPw(e.target.value) }} />
            </div><br />
            <div className="form-group">
              <label htmlFor="email" className="form-label">Email </label><br />
              <div className="input-group mb-1">
                <input type="email" className={`form-control ${checkEmail}`} required
                  value={email} onChange={(e) => { setEmail(e.target.value) }} />
                <button type='click' className="input-group-text" onClick={codeClick}>Send verified code </button>
              </div>
              <div className={`${codeVerify}`}>
                <span style={{ fontSize:"small" }}>Please check your email</span>
                <div className="input-group input-group-sm w-50">
                  <input type="text" className={`form-control`} placeholder="Verified code" aria-label="Server" 
                  value={code} onChange={(e)=> { setCode(e.target.value)}}/>
                  <button type='click' className="input-group-text" onClick={verifySubmit}>Verify </button>
                  <span className={`${time} ms-2`}><strong>Time left</strong> {formatTime(timeLeft)}</span>
                  <button type='click' className={`input-group-text ${resendButton} ms-2`} onClick={resendClick}>Resend</button>
                </div>
              </div>
            </div><br />
            <div className="form-group">
              <label htmlFor="name" className="form-label">Name </label><br />
              <input type="text" id="name" className="form-control" required
                value={fullname} onChange={(e) => { setName(e.target.value) }} />
            </div>
            <a href="/login" className="w-100 link-secondary">Already have account?</a>
            <br /><br />
            <div className="col-12 text-center">
              <button type="submit" className="btn btn-primary mb-2">Sign up</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  )
}

export default SignUp