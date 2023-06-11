import React, {useContext, useState} from 'react';
import {Box, Typography, TextField, Button, Avatar, Grid} from '@mui/material';
import UserContext from "../../context/user-context";
import {loginUser} from "../../services/apiService";
import {useNavigate} from 'react-router-dom';

const LoginPage = () => {
    let navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [emailError, setEmailError] = useState(false);
    const [password, setPassword] = useState('');
    const userCtx = useContext(UserContext);

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
        const isValidEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(event.target.value);
        setEmailError(!isValidEmail);
    };

    const handleSubmit = async (values) => {

        const response = loginUser(values);
        userCtx.setUserCredentials(response.data)
        navigate('/dashboard')

    };

    return (
        <Grid container sx={{height: '100vh'}}>
            <Grid item xs={12} sm={6} sx={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                p: 5,
                backgroundColor: '#FFF',
                boxShadow: '0px 0px 20px rgba(0,0,0,0.1)'
            }}>
                <Box sx={{width: '70%'}}>
                    <Typography variant="h4">Sign in Account</Typography>

                    <TextField
                        label="Email"
                        type="email"
                        variant="outlined"
                        fullWidth
                        error={emailError}
                        helperText={emailError ? 'Invalid email' : ''}
                        value={email}
                        onChange={handleEmailChange}
                        sx={{mt: 3}}
                    />

                    <TextField
                        label="Password"
                        type="password"
                        variant="outlined"
                        fullWidth
                        value={password}
                        sx={{mt: 3}}
                        onChange={(e) => setPassword(e.target.value)}
                    />

                    <Typography color="primary" sx={{mt: 1, cursor: 'pointer'}}>
                        Forgot password?
                    </Typography>

                    <Button
                        variant="contained"
                        color="primary"
                        fullWidth
                        sx={{mt: 3, borderRadius: '12px'}}>
                        Sign in
                    </Button>

                    <Typography variant="body1" sx={{mt: 3, fontSize: '18px', fontWeight: 'bold'}}>
                        Still don't have an account? <span style={{color: 'blue'}}>Sign up!</span>
                    </Typography>
                </Box>
            </Grid>

            <Grid item xs={12} sm={6} sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                backgroundColor: '#afdc77',
                color: 'black',
                p: 5
            }}>
                <Box sx={{width: '70%'}}>
                    <Typography variant="h4" sx={{fontWeight: 'bold', fontSize: '28px'}}>
                        Explore the best system for providing personalized service in the service industry
                    </Typography>

                    <Typography variant="body1" sx={{mt: 3}}>
                        Hundreds of businesses have already joined us and have greatly improved their customer service!
                        Join us too!
                    </Typography>

                    <Box sx={{display: 'flex', justifyContent: 'center', mt: 3, '& > *': {m: 0.5}}}>
                        <Avatar/> <Avatar/> <Avatar/> <Avatar/>
                    </Box>

                    <Box sx={{display: 'flex', alignItems: 'center', mt: 3}}>
                        <hr style={{flex: '1'}}/>
                        <Typography variant="h6" sx={{mx: 2}}>150+ happy customers!</Typography>
                        <hr style={{flex: '1'}}/>
                    </Box>
                </Box>
            </Grid>
        </Grid>
    );
}

export default LoginPage;
