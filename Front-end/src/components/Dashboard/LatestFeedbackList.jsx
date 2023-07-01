import React, {useState, useEffect} from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import {fetchTemplate, fetchUserProfile} from "../../services/apiService";

export function LatestFeedbackList({data}) {
    const [open, setOpen] = useState(false);
    const [selectedFeedback, setSelectedFeedback] = useState(null);
    const [userData, setUserData] = useState(null);
    const [templateData, setTemplateData] = useState(null);

    const handleOpen = (feedback) => {
        setSelectedFeedback(feedback);
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    useEffect(() => {
        if (selectedFeedback) {
            fetchUserData(selectedFeedback.user_id);
            fetchTemplateData(selectedFeedback.template_id);
        }
    }, [selectedFeedback]);

    const fetchUserData = async (userId) => {
        const response = await fetchUserProfile(userId);
        setUserData(response);
    };

    const fetchTemplateData = async (templateId) => {
        const response = await fetchTemplate(templateId);
        setTemplateData(response);
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
            <h2>Latest Feedback</h2>
            {data.map((feedback) => (
                <Card key={feedback.id}>
                    <CardContent>
                        <Typography color="textSecondary" gutterBottom>
                            Reviewed at: {new Date(feedback.reviewed_at).toLocaleString()}
                        </Typography>
                        <Typography variant="h5" component="div">
                            Rating: {feedback.rating}/5
                        </Typography>
                        <Typography variant="body2" component="p">
                            Review: {feedback.review_text}
                        </Typography>
                        <Button variant="outlined" onClick={() => handleOpen(feedback)}>Подробнее</Button>
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
                    {selectedFeedback && (
                        <div>
                            <h2>Review Details</h2>
                            <p>Review Text: {selectedFeedback.review_text}</p>
                            <p>Reviewed at: {new Date(selectedFeedback.reviewed_at).toLocaleString()}</p>
                            <p>Rating: {selectedFeedback.rating}</p>
                        </div>
                    )}
                </Box>
            </Modal>
        </div>
    );
}
