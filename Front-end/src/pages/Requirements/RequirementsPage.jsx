import React, { useEffect, useState,useContext } from 'react';
import Sidebar from "../../components/Sidebar";
import { Box } from "@mui/material";
import { fetchBusinessSupportedRequirements, postNewRequirement, deleteRequirement } from "../../services/apiService";
import RequirementsList from '../../components/Requirements/RequirementsList';
import AddRequirement from '../../components/Requirements/AddRequirement';
import UserContext from '../../context/user-context';

function RequirementsPage() {
    const [requirements, setRequirements] = useState([]);
    const { businessId } = useContext(UserContext);


    useEffect(() => {
        fetchBusinessSupportedRequirements(businessId).then(supportedRequirements => {
            setRequirements(supportedRequirements);
        });
    }, []);

    const handleAddRequirement = async (newRequirement) => {
        const updatedRequirements = await postNewRequirement(businessId, [newRequirement]);
        setRequirements(prev => [...prev, newRequirement]);
    };

    const handleDeleteRequirement = async (requirementToDelete) => {
        await deleteRequirement(businessId, requirementToDelete.id);
        setRequirements(prev => prev.filter(requirement => requirement.id !== requirementToDelete.id));
    };

    return (
        <Box sx={{ display: 'flex' }}>
            <Sidebar/>
            <Box component="main" sx={{ flexGrow: 1, ml: { sm: 35 }, m: 3, width: '100%' }}>
                <AddRequirement onAdd={handleAddRequirement} currentRequirements={requirements} />
                <RequirementsList requirements={requirements} onDelete={handleDeleteRequirement} />
            </Box>
        </Box>
    );
}

export default RequirementsPage;
