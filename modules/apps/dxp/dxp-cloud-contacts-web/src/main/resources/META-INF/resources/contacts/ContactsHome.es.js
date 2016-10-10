import Component from 'metal-component';
import templates from './ContactsHome.soy';
import Soy from 'metal-soy';

import 'dxp-cloud-sidebar/DXPSidebar.es';
import 'dxp-cloud-topbar/DXPTopbar.es';

class ContactsHome extends Component {
}

Soy.register(ContactsHome, templates);

export default ContactsHome;