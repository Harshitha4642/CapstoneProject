'use strict';

customElements.define('compodoc-menu', class extends HTMLElement {
    constructor() {
        super();
        this.isNormalMode = this.getAttribute('mode') === 'normal';
    }

    connectedCallback() {
        this.render(this.isNormalMode);
    }

    render(isNormalMode) {
        let tp = lithtml.html(`
        <nav>
            <ul class="list">
                <li class="title">
                    <a href="index.html" data-type="index-link">capstone-front documentation</a>
                </li>

                <li class="divider"></li>
                ${ isNormalMode ? `<div id="book-search-input" role="search"><input type="text" placeholder="Type to search"></div>` : '' }
                <li class="chapter">
                    <a data-type="chapter-link" href="index.html"><span class="icon ion-ios-home"></span>Getting started</a>
                    <ul class="links">
                        <li class="link">
                            <a href="overview.html" data-type="chapter-link">
                                <span class="icon ion-ios-keypad"></span>Overview
                            </a>
                        </li>
                        <li class="link">
                            <a href="index.html" data-type="chapter-link">
                                <span class="icon ion-ios-paper"></span>README
                            </a>
                        </li>
                                <li class="link">
                                    <a href="dependencies.html" data-type="chapter-link">
                                        <span class="icon ion-ios-list"></span>Dependencies
                                    </a>
                                </li>
                                <li class="link">
                                    <a href="properties.html" data-type="chapter-link">
                                        <span class="icon ion-ios-apps"></span>Properties
                                    </a>
                                </li>
                    </ul>
                </li>
                    <li class="chapter modules">
                        <a data-type="chapter-link" href="modules.html">
                            <div class="menu-toggler linked" data-bs-toggle="collapse" ${ isNormalMode ?
                                'data-bs-target="#modules-links"' : 'data-bs-target="#xs-modules-links"' }>
                                <span class="icon ion-ios-archive"></span>
                                <span class="link-name">Modules</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                        </a>
                        <ul class="links collapse " ${ isNormalMode ? 'id="modules-links"' : 'id="xs-modules-links"' }>
                            <li class="link">
                                <a href="modules/AppModule.html" data-type="entity-link" >AppModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ?
                                            'data-bs-target="#components-links-module-AppModule-3d701e7f45dd79a1d34c311b44c513245bc31738f13b2734610713979c1198ec25cae39c9af851b86d9666750819811d14b1f00959f8bccaed8ee5644befa865"' : 'data-bs-target="#xs-components-links-module-AppModule-3d701e7f45dd79a1d34c311b44c513245bc31738f13b2734610713979c1198ec25cae39c9af851b86d9666750819811d14b1f00959f8bccaed8ee5644befa865"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-AppModule-3d701e7f45dd79a1d34c311b44c513245bc31738f13b2734610713979c1198ec25cae39c9af851b86d9666750819811d14b1f00959f8bccaed8ee5644befa865"' :
                                            'id="xs-components-links-module-AppModule-3d701e7f45dd79a1d34c311b44c513245bc31738f13b2734610713979c1198ec25cae39c9af851b86d9666750819811d14b1f00959f8bccaed8ee5644befa865"' }>
                                            <li class="link">
                                                <a href="components/AppComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >AppComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/AppHomeComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >AppHomeComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/CallComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >CallComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/CallFormComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >CallFormComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/HeaderComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >HeaderComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/LoginComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >LoginComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/MessageComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >MessageComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/SearchComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >SearchComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/SearchResultsComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >SearchResultsComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/AppRoutingModule.html" data-type="entity-link" >AppRoutingModule</a>
                            </li>
                </ul>
                </li>
                        <li class="chapter">
                            <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#injectables-links"' :
                                'data-bs-target="#xs-injectables-links"' }>
                                <span class="icon ion-md-arrow-round-down"></span>
                                <span>Injectables</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                            <ul class="links collapse " ${ isNormalMode ? 'id="injectables-links"' : 'id="xs-injectables-links"' }>
                                <li class="link">
                                    <a href="injectables/AuthenticationService.html" data-type="entity-link" >AuthenticationService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/CallService.html" data-type="entity-link" >CallService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/LocationService.html" data-type="entity-link" >LocationService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/MessageService.html" data-type="entity-link" >MessageService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/SearchService.html" data-type="entity-link" >SearchService</a>
                                </li>
                            </ul>
                        </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#interfaces-links"' :
                            'data-bs-target="#xs-interfaces-links"' }>
                            <span class="icon ion-md-information-circle-outline"></span>
                            <span>Interfaces</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? ' id="interfaces-links"' : 'id="xs-interfaces-links"' }>
                            <li class="link">
                                <a href="interfaces/AppLocation.html" data-type="entity-link" >AppLocation</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/AppStatus.html" data-type="entity-link" >AppStatus</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/AppType.html" data-type="entity-link" >AppType</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/CallSearchResult.html" data-type="entity-link" >CallSearchResult</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/CSVCallObject.html" data-type="entity-link" >CSVCallObject</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Form.html" data-type="entity-link" >Form</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/Item.html" data-type="entity-link" >Item</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/MessageForm.html" data-type="entity-link" >MessageForm</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/MessageSearchResult.html" data-type="entity-link" >MessageSearchResult</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/MessageType.html" data-type="entity-link" >MessageType</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/SearchContent.html" data-type="entity-link" >SearchContent</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <a data-type="chapter-link" href="routes.html"><span class="icon ion-ios-git-branch"></span>Routes</a>
                        </li>
                    <li class="chapter">
                        <a data-type="chapter-link" href="coverage.html"><span class="icon ion-ios-stats"></span>Documentation coverage</a>
                    </li>
                    <li class="divider"></li>
                    <li class="copyright">
                        Documentation generated using <a href="https://compodoc.app/" target="_blank" rel="noopener noreferrer">
                            <img data-src="images/compodoc-vectorise.png" class="img-responsive" data-type="compodoc-logo">
                        </a>
                    </li>
            </ul>
        </nav>
        `);
        this.innerHTML = tp.strings;
    }
});