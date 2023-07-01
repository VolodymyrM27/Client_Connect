import React, { useEffect, useState, useContext } from 'react';
import { Container, Typography, Grid, Chip, Button } from '@mui/material';
import Sidebar from '../../components/Sidebar';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { fetchUserProfile, fetchBusinessSupportedRequirements } from '../../services/apiService';
import { useTheme } from '@mui/material/styles';
import useMediaQuery from '@mui/material/useMediaQuery';
import UserContext from '../../context/user-context';

function CardDetector() {
    const [template, setTemplate] = useState(null);
    const [userProfile, setUserProfile] = useState(null);
    const [businessRequirements, setBusinessRequirements] = useState([]);
    const theme = useTheme();
    const isSmallScreen = useMediaQuery(theme.breakpoints.down('md'));
    const { businessId } = useContext(UserContext);


    const containerStyles = {
        paddingLeft: isSmallScreen ? theme.spacing(2) : theme.spacing(7),
        transition: theme.transitions.create(['padding'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    }

    useEffect(() => {
        const socket = new SockJS('http://localhost:8080/ws')
        const stompClient = Stomp.over(socket);

        let userData = JSON.parse(localStorage.getItem("userData"));
        const thisheaders = {
            Authorization: `Bearer ${userData && userData.token}`,
        };

        stompClient.connect(thisheaders, function (frame) {
            stompClient.subscribe('/topic/'+businessId+'/template', function (greeting) {
                setTemplate(JSON.parse(greeting.body));
            });
        });

        // Отключение при размонтировании
        return () => {
            stompClient.disconnect();
        };
    }, []);

    useEffect(() => {
        if (template) {
            const fetchProfile = async () => {
                const profile = await fetchUserProfile(template.user_id);
                console.log(profile)
                setUserProfile(profile);
            };
            fetchProfile();
        }
    }, [template]);

    useEffect(() => {
        const fetchRequirements = async () => {
            const requirements = await fetchBusinessSupportedRequirements(businessId);
            console.log(requirements)
            setBusinessRequirements(requirements);
        };
        fetchRequirements();
    }, []);

    const requirementChips = template && template.template_requirements.map((req) => {
        const isSupported = businessRequirements.some((br) => br.requirement_name === req.requirement_name);
        return (
            <Chip
                key={req.requirement_name}
                label={req.requirement_name}
                color={isSupported ? "success" : "error"}
            />
        );
    });

    const handleAccept = () => {
        // Здесь вы можете добавить свой код для отправки запроса на сервер об акцептации шаблона
        console.log("Accepted");
    };

    const handleReject = () => {
        // Здесь вы можете добавить свой код для отправки запроса на сервер об отклонении шаблона
        console.log("Rejected");
    };

    return (
        <Grid container>
            <Grid item xs={12} md={2}>
                <Sidebar />
            </Grid>
            <Grid item xs={12} md={10}>
                <Container sx={containerStyles}>
                    <Typography variant="h4">Card Detector</Typography>
                    {userProfile && (
                        <>
                            <Typography variant="h5">User Profile:</Typography>
                            <Typography>First Name: {userProfile.first_name}</Typography>
                            <Typography>Last Name: {userProfile.last_name}</Typography>
                            <Typography>Date of Birth: {userProfile.date_of_birth}</Typography>
                            <Typography>Gender: {userProfile.gender}</Typography>
                            <Typography>Contact Number: {userProfile.contact_number}</Typography>
                            <Typography>Country: {userProfile.country}</Typography>
                            <Typography>State: {userProfile.state}</Typography>
                            <Typography>City: {userProfile.city}</Typography>

                        </>
                    )}
                    <Typography variant="h5">Template Requirements:</Typography>
                    {requirementChips}
                </Container>
            </Grid>
        </Grid>
    );
}

export default CardDetector;
