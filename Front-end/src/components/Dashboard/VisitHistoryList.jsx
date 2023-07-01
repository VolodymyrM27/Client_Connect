import React, {useEffect, useState} from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import {fetchTemplate, fetchUserProfile} from "../../services/apiService";

export function VisitHistoryList({ data }) {
    const [open, setOpen] = useState(false);
    const [userData, setUserData] = useState(null);
    const [templateData, setTemplateData] = useState(null);

    const handleOpen = async ({userId, template_id}) => {
        const userProfile = await fetchUserProfile(userId);
        setUserData(userProfile);

        const template = await fetchTemplate(template_id);
        setTemplateData(template);

        setOpen(true);
    };


    const handleClose = () => {
        setOpen(false);
    };

    const modalStyle = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 400,
        bgcolor: 'background.paper',
        boxShadow: 24,
        p: 4,
    };

    return (
        <div>
            <h2>Visit History</h2>
            {data.map((visit) => (
                <Card key={visit.id}>
                    <CardContent>
                        <Typography color="textSecondary" gutterBottom>
                            {new Date(visit.used_at).toLocaleString()}
                        </Typography>
                        <Typography variant="h5" component="div">
                            {visit.business_business_name}
                        </Typography>
                        <Button variant="outlined" onClick={() => handleOpen({userId: visit.user_id, template_id: visit.template_id})}>Подробнее</Button>
                    </CardContent>
                </Card>
            ))}
            <Modal open={open} onClose={handleClose}>
                <Box sx={modalStyle}>
                    {userData && (
                        <div>
                            <h2>User Details</h2>
                            <p>First Name: {userData.first_name}</p>
                            <p>Last Name: {userData.last_name}</p>
                            <p>Date of Birth: {userData.date_of_birth}</p>
                            <p>Gender: {userData.gender}</p>
                            <p>Contact Number: {userData.contact_number}</p>
                            <p>Country: {userData.country}</p>
                            <p>State: {userData.state}</p>
                            <p>City: {userData.city}</p>
                        </div>
                    )}
                    {templateData && (
                        <div>
                            <h2>Template Details</h2>
                            {templateData.template_requirements.map((requirement, index) => (
                                <div key={index}>
                                    <p>Requirement Name: {requirement.requirement_name}</p>
                                    <p>Requirement Value: {requirement.requirement_value}</p>
                                </div>
                            ))}
                        </div>
                    )}
                </Box>
            </Modal>
        </div>
    );
}
