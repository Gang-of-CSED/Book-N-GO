import logo from './logo.svg';
import './App.css';
import {
  BrowserRouter as Router,
  Routes,
  Route,
} from "react-router-dom";
import { HallDetails } from './HallDetails/HallDetails';

function App() {
  return (
    <Router>
      <Routes>
        {/* <Route path='/' element={<HomePage/>}/> */}
        {/* <Route path='/workspace' element={<WorkSpace/>}/> */}
        <Route path='/hall/:id' element={<HallDetails/>}/>
        {/* <Route path='/login' element={<LoginPage/>}/> */}

      </Routes>
    </Router>
  );
}

export default App;
