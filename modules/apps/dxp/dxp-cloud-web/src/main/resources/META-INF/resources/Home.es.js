import Component from 'metal-component';
import templates from './Home.soy';
import Soy from 'metal-soy';

import 'dxp-cloud-sidebar/DXPSidebar.es';
import 'dxp-cloud-topbar/DXPTopbar.es';

class Home extends Component {
}

Soy.register(Home, templates);

export default Home;