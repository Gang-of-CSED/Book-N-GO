import logo from './logo.svg';
import './App.css';
import {
  Navigate,
  BrowserRouter as Router,
  Routes,
  Route,
} from "react-router-dom";
import './App.css';
import { HallDetails } from './HallDetails/HallDetails';
import { SignUp } from './Sign/SignUpPage/SignUp';
import { Login } from './Sign/LoginPage/Login';
import {WorkSpace} from "./WorkSpace/WorkSpace";
import {Reservations} from "./Reservations/Reservations";

import { HallsList} from './HallsList&Filter/HallsListPage/HallsList';
import { SelectRole } from './Sign/SelectRolePage/SelectRole';


function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Navigate to="/login" />} />
        {/* <Route path='/' element={<HomePage/>}/> */}
        { <Route path='/workspace' element={<WorkSpace/>}/> }
          { <Route path='/workspace/:workspaceId' element={<WorkSpace/>}/> }
          {<Route path='/reservations' element={<Reservations/>}/>}
        {/* {<Route path='/hall' element={<HallDetails/>}/>} */}
        {<Route path='/login' element={<Login/>}/>}
        {<Route path='/signup' element={<SignUp/>}/>}
        <Route path='/workspace/:workspaceId/hall/:id' element={<HallDetails/>}/>
        {<Route path="/select-role" element={<SelectRole />} />}
        {/* <Route path='/login' element={<LoginPage/>}/> */}
        <Route path='/hallsList' element={<HallsList/>}/>
      </Routes>
    </Router>
  );
}

export default App;
