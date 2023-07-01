import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from './pages/Login/LoginPage';
import RequirementsPage from './pages/Requirements/RequirementsPage';
import StatisticsPage from "./pages/Dashboard/StatisticsPage";
import ProtectedRoute from "./routes/ProtectedRoute";
import Terminals from "./pages/Terminals/Terminals";
import CardDetector from "./pages/CardDetector/CardDetectorPage";
import CreateEmployeePage from "./pages/CreateEmployee/CreateEmployeePage";
import AllEmployeesPage from "./pages/AllEmployees/AllEmployees";
import RegisterPage from "./pages/Register/RegisterPage";

function App() {
  return (
      <Router>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/dashboard" element={<ProtectedRoute roles={["BUSINESS"]}>
                <StatisticsPage/>
            </ProtectedRoute>}/>
            <Route path="/requirements" element={<ProtectedRoute roles={["BUSINESS"]}>
                <RequirementsPage/>
            </ProtectedRoute>}/>

            <Route path="/terminal" element={<ProtectedRoute roles={["BUSINESS"]}>
                <Terminals/>
            </ProtectedRoute>}/>

            <Route path="/card-detector" element={<ProtectedRoute roles={["BUSINESS"]}>
                <CardDetector/>
            </ProtectedRoute>}/>

            <Route path="/create-employee" element={<ProtectedRoute roles={["BUSINESS"]}>
                <CreateEmployeePage/>
            </ProtectedRoute>}/>

            <Route path="/all-employees" element={<ProtectedRoute roles={["BUSINESS"]}>
                <AllEmployeesPage/>
            </ProtectedRoute>}/>


        </Routes>
      </Router>
  );
}

export default App;
